package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@WebSocket
@RequiredArgsConstructor
@Slf4j
public class JettyWebSocket {

    private static final int REQUEST_ID_INDEX = 1;

    private final Map<String, TypedCallResultFutureContainer<?>> requestIdsToResults = new ConcurrentHashMap<>();

    private final ReadWriteLock callsToResultsReadWriteLock = new ReentrantReadWriteLock(true);

    private final ObjectMapper objectMapper;

    private Session session;

    private final Semaphore wsSessionSemaphore = new Semaphore(1);

    @OnWebSocketConnect
    public void onWebSocketConnect(Session session) {
        setSession(session);
    }

    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason) {
        setSession(null);
    }

    @OnWebSocketMessage
    public void onMessage(String rawMessage) {
        final String requestId;
        try {
            requestId = getRequestIdFromRawMessage(rawMessage);
        } catch (JsonProcessingException | IndexOutOfBoundsException e) {
            log.error(e.getMessage(), e);
            return;
        }

        try {
            doInReadLock(results -> {
                if (!results.containsKey(requestId)) {
                    return;
                }
                parseRawMessageToResultAndCompleteFuture(results.get(requestId), rawMessage);
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            removeResultForId(requestId);
        }
    }

    public <T, U> CompletableFuture<CallResult<U>> sendCall(Call<T> call, Class<U> expectedCallResultClass) {
        CompletableFuture<CallResult<U>> futureResult = new CompletableFuture<>();
        doInWriteLock(results -> results.put(
            call.getUniqueId(),
            new TypedCallResultFutureContainer<>(futureResult, expectedCallResultClass)
        ));
        trySendStringToSession(tryWriteValueAsString(call), session);
        return futureResult;
    }

    public boolean isOpen() {
        return accessSessionInLock(wsSession -> wsSession != null && wsSession.isOpen());
    }

    public void close() {
        accessSessionInLock(wsSession -> {
           if (wsSession != null && wsSession.isOpen()) {
               wsSession.close();
           }
           return (Void) null;
        });
    }

    private <T> void parseRawMessageToResultAndCompleteFuture(
        TypedCallResultFutureContainer<T> completableFutureContainer, String rawMessage
    ) {
        completableFutureContainer.getCallResultFuture().complete(tryReadCallResultFrom(rawMessage, completableFutureContainer.getExpectedCallResultClass()));
    }

    private void removeResultForId(String requestUniqueId) {
        doInWriteLock(results -> results.remove(requestUniqueId));
    }

    private void doInWriteLock(Consumer<Map<String, TypedCallResultFutureContainer<?>>> resultsAccessor) {
        var lock = callsToResultsReadWriteLock.writeLock();
        lock.lock();
        try {
            resultsAccessor.accept(requestIdsToResults);
        } finally {
            lock.unlock();
        }
    }

    private void doInReadLock(Consumer<Map<String, TypedCallResultFutureContainer<?>>> resultsAccessor) {
        var lock = callsToResultsReadWriteLock.readLock();
        lock.lock();
        try {
            resultsAccessor.accept(requestIdsToResults);
        } finally {
            lock.unlock();
        }
    }

    private void trySendStringToSession(String string, Session wsSession) {
        trySendStringToRemote(
            string,
            Optional.of(wsSession).map(Session::getRemote).orElseThrow(ThrowReadablyUtil::emptyOptionalException)
        );
    }

    private void trySendStringToRemote(String string, RemoteEndpoint remoteEndpoint) {
        try {
            remoteEndpoint.sendString(string);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }

    private String tryWriteValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }

    private <T> CallResult<T> tryReadCallResultFrom(String json, Class<T> expectedPayloadClass) {
        try {
            return objectMapper.readValue(
                json,
                TypeFactory.defaultInstance().constructParametricType(CallResult.class, expectedPayloadClass)
            );
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }

    private String getRequestIdFromRawMessage(String rawMessage)
        throws JsonProcessingException, IndexOutOfBoundsException {
        return String.valueOf(objectMapper.readValue(rawMessage, Object[].class)[REQUEST_ID_INDEX]);
    }

    private void setSession(Session session) {
        try {
            wsSessionSemaphore.acquire();
            this.session = session;
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw unchecked(e);
        } finally {
            wsSessionSemaphore.release();
        }
    }

    private <T> T accessSessionInLock(Function<Session, T> sessionAccessor) {
        try {
            wsSessionSemaphore.acquire();
            return sessionAccessor.apply(session);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw unchecked(e);
        } finally {
            wsSessionSemaphore.release();
        }
    }

    @RequiredArgsConstructor
    @Getter
    private static class TypedCallResultFutureContainer<T> {

        @NonNull
        private final CompletableFuture<CallResult<T>> callResultFuture;

        @NonNull
        private final Class<T> expectedCallResultClass;
    }
}
