package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.AsyncCentralSystemClient;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationConnectionException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ConcurrencyUtil;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@WebSocket
@RequiredArgsConstructor
@Slf4j
public class JettyWebsocketClient implements AsyncCentralSystemClient {

    private static final int REQUEST_ID_INDEX = 1;

    private final Map<String, TypedCallResultFutureContainer<?>> requestIdsToResults = new ConcurrentHashMap<>();

    private final ReadWriteLock callsToResultsReadWriteLock = new ReentrantReadWriteLock(true);

    private final ObjectMapper objectMapper;

    private final WebSocketClient webSocketClient;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Session session;

    private final Semaphore wsSessionSemaphore = new Semaphore(1);

    @Override
    public void connect(URI centralSystemUri) throws EmulationConnectionException {
        try {
            connectAsync(centralSystemUri).get();
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new EmulationConnectionException(e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new EmulationConnectionException(e);
        }
    }

    @Override
    public CompletableFuture<Session> connectAsync(URI centralSystemUri) {
        try {
            return webSocketClient.connect(this, centralSystemUri);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return CompletableFuture.failedFuture(e);
        }
    }

    @Override
    public void disconnect() {
        ConcurrencyUtil.acquireRunRelease(wsSessionSemaphore, () -> {
            Optional.ofNullable(getSession()).ifPresent(currentSession -> {
                if (currentSession.isOpen()) {
                    currentSession.close();
                }
                currentSession.disconnect();
            });
            setSession(null);
        });
    }

    @Override
    public <T, U> CompletableFuture<CallResult<U>> sendCallAsync(Call<T> call, Class<U> expectedCallResultClass) {
        CompletableFuture<CallResult<U>> futureResult = new CompletableFuture<>();
        doInWriteLock(results -> results.put(
            call.getUniqueId(),
            new TypedCallResultFutureContainer<>(futureResult, expectedCallResultClass)
        ));
        trySendStringToSession(tryWriteValueAsString(call), session);
        return futureResult;
    }

    @Override
    public <T, U> CallResult<U> sendCall(Call<T> call, Class<U> expectedCallResultClass) {
        try {
            return sendCallAsync(call, expectedCallResultClass).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw unchecked(e);
        } catch (ExecutionException e) {
            throw unchecked(e);
        }
    }

    @OnWebSocketConnect
    public void onWebSocketConnect(Session session) {
        setSessionThreadSafe(session);
    }

    @OnWebSocketClose
    public void onWebSocketClose(int statusCode, String reason) {
        setSessionThreadSafe(null);
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

    private void setSessionThreadSafe(Session session) {
        ConcurrencyUtil.acquireRunRelease(wsSessionSemaphore, () -> setSession(session));
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
