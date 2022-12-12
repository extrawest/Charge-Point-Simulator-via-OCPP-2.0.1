package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.*;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.*;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unableToConnect;
import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Slf4j
public class WebSocketChargePointEmulator implements ChargePointEmulator {

    private final CallFactory callFactory;

    private final ScheduledExecutorService scheduledExecutorService;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final WebSocketClient webSocketClient;

    private final JettyWebSocket jettyWebSocket;

    private final Semaphore emulationStateSemaphore = new Semaphore(1);

    private final URI connectionUri;

    private ScheduledFuture<?> heartbeatScheduledFuture;

    public WebSocketChargePointEmulator(
        CallFactory callFactory,
        String centralSystemUrl,
        String chargePointId,
        ScheduledExecutorService scheduledExecutorService,
        String chargePointModel,
        String chargePointVendor,
        ObjectMapper objectMapper,
        WebSocketClient webSocketClient
    ) {
        this.callFactory = callFactory;
        this.scheduledExecutorService = scheduledExecutorService;
        this.chargePointModel = chargePointModel;
        this.chargePointVendor = chargePointVendor;
        this.webSocketClient = webSocketClient;
        this.connectionUri = URI.create(centralSystemUrl).resolve(chargePointId);
        this.jettyWebSocket = new JettyWebSocket(objectMapper);
    }

    @Override
    public void start() throws EmulationException {
        try {
            emulationStateSemaphore.acquire();
            if (jettyWebSocket.isOpen()) {
                throw new EmulationException("The emulator is already started");
            }
            webSocketClient.connect(jettyWebSocket, connectionUri)
                .thenComposeAsync(
                    readySession -> jettyWebSocket.sendCall(
                        callFactory.createCallFor(new BootNotificationRequest(chargePointModel, chargePointVendor)),
                        BootNotificationConfirmation.class
                    )
                )
                .thenApply(CallResult::getPayload)
                .thenAcceptAsync(this::scheduleHeartbeat, scheduledExecutorService)
                .get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw unableToConnect(String.valueOf(connectionUri));
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new EmulationException(e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new EmulationException(e);
        } finally {
            emulationStateSemaphore.release();
        }
    }

    @Override
    public void stop() {
        try {
            emulationStateSemaphore.acquire();
            if (!jettyWebSocket.isOpen()) {
                return;
            }
            if (heartbeatScheduledFuture != null) {
                heartbeatScheduledFuture.cancel(true);
            }
            jettyWebSocket.close();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw unchecked(e);
        } finally {
            emulationStateSemaphore.release();
        }
    }

    private void scheduleHeartbeat(BootNotificationConfirmation bootNotificationConfirmation) {
        if (bootNotificationConfirmation.getStatus() != RegistrationStatus.Accepted) {
            throw unchecked(new EmulationException("BootNotificationConfirmation status is not Accepted"));
        }
        heartbeatScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(
            () -> jettyWebSocket.sendCall(
                callFactory.createCallFor(new HeartbeatRequest()),
                HeartbeatConfirmation.class
            ),
            bootNotificationConfirmation.getInterval(),
            bootNotificationConfirmation.getInterval(),
            TimeUnit.SECONDS
        );
    }
}
