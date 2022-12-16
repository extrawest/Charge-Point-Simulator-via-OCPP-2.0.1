package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.AsyncCentralSystemClient;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.*;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ConcurrencyUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.*;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Slf4j
public class ChargePointEmulatorImpl implements ChargePointEmulator {

    private final CallFactory callFactory;

    private final ScheduledExecutorService scheduledExecutorService;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final AsyncCentralSystemClient csClient;

    private final Semaphore heartbeatScheduledFutureSemaphore = new Semaphore(1);

    private final URI connectionUri;

    private ScheduledFuture<?> heartbeatScheduledFuture;

    public ChargePointEmulatorImpl(
        CallFactory callFactory,
        String centralSystemUrl,
        String chargePointId,
        ScheduledExecutorService scheduledExecutorService,
        String chargePointModel,
        String chargePointVendor,
        AsyncCentralSystemClient csClient
    ) {
        this.callFactory = callFactory;
        this.scheduledExecutorService = scheduledExecutorService;
        this.chargePointModel = chargePointModel;
        this.chargePointVendor = chargePointVendor;
        this.csClient = csClient;
        this.connectionUri = URI.create(centralSystemUrl).resolve(chargePointId);
    }

    @Override
    public void start() throws EmulationException {
        try {
            csClient.connect(connectionUri);
            csClient.sendCallAsync(
                    callFactory.createCallFor(new BootNotificationRequest(chargePointModel, chargePointVendor)),
                    BootNotificationConfirmation.class
                )
                .thenApply(CallResult::getPayload)
                .thenAcceptAsync(this::scheduleHeartbeat, scheduledExecutorService)
                .get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new EmulationException(e);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new EmulationException(e);
        }
    }

    @Override
    public void stop() {
        ConcurrencyUtil.acquireRunRelease(heartbeatScheduledFutureSemaphore, () -> {
            if (heartbeatScheduledFuture != null) {
                heartbeatScheduledFuture.cancel(true);
            }
        });
        csClient.disconnect();
    }

    private void scheduleHeartbeat(BootNotificationConfirmation bootNotificationConfirmation) {
        if (bootNotificationConfirmation.getStatus() != RegistrationStatus.Accepted) {
            throw unchecked(new EmulationException("BootNotificationConfirmation status is not Accepted"));
        }
        ConcurrencyUtil.acquireRunRelease(
            heartbeatScheduledFutureSemaphore,
            () -> heartbeatScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(
                () -> csClient.sendCall(
                    callFactory.createCallFor(new HeartbeatRequest()),
                    HeartbeatConfirmation.class
                ),
                bootNotificationConfirmation.getInterval(),
                bootNotificationConfirmation.getInterval(),
                TimeUnit.SECONDS
            )
        );
    }
}
