package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.CentralSystemUnavailableException;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.IClientAPI;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unableToConnect;
import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@RequiredArgsConstructor
@Slf4j
public class ChargePointEmulatorImpl implements ChargePointEmulator {

    private final String centralSystemUrl;

    private final IClientAPI ocppClient;

    private final ClientEvents ocppClientEvents;

    private final ClientCoreProfile ocppClientCoreProfile;

    private final ScheduledExecutorService scheduledExecutorService;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final String chargePointId;

    private ScheduledFuture<?> heartbeatScheduledFuture;

    @Override
    public void start() throws CentralSystemUnavailableException {
        tryConnectOrThrow();
        var heartbeatIntervalSeconds = Optional.of(trySendBootNotificationRequestOrThrow())
            .map(this::validateAndGetHeartbeatIntervalSecondsOrThrow)
            .orElseThrow(ThrowReadablyUtil::emptyOptionalException);
        scheduleHeartbeat(heartbeatIntervalSeconds);
    }

    @Override
    public void stop() {
        Optional.ofNullable(heartbeatScheduledFuture)
            .ifPresent(scheduledFuture -> scheduledFuture.cancel(true));
        ocppClient.disconnect();
    }

    private void tryConnectOrThrow() throws CentralSystemUnavailableException {
        var chargePointWebSocketTopicUrl = centralSystemUrl + "/" + chargePointId;
        ocppClient.connect(chargePointWebSocketTopicUrl, ocppClientEvents);
        if (ocppClient.isClosed()) {
            throw unableToConnect(chargePointWebSocketTopicUrl);
        }
    }

    private BootNotificationConfirmation sendBootNotificationRequest()
        throws OccurenceConstraintException, UnsupportedFeatureException, ExecutionException, InterruptedException {
        var bootNotificationRequest =
            ocppClientCoreProfile.createBootNotificationRequest(chargePointVendor, chargePointModel);
        return ocppClient.send(bootNotificationRequest)
            .thenApply(confirmation -> {
                var bootNotificationConfirmation = (BootNotificationConfirmation) confirmation;
                log.debug(
                    "ChargePointEmulator (id=%s): BootNotificationConfirmation received: %s".formatted(
                        chargePointId, bootNotificationConfirmation
                    )
                );
                return bootNotificationConfirmation;
            })
            .toCompletableFuture()
            .get();
    }

    private void scheduleHeartbeat(int heartbeatIntervalSeconds) {
        log.debug("Scheduling heartbeat interval for %s seconds".formatted(heartbeatIntervalSeconds));
        heartbeatScheduledFuture = scheduledExecutorService.scheduleAtFixedRate(
            this::trySendHeartbeatOrThrow, heartbeatIntervalSeconds, heartbeatIntervalSeconds, TimeUnit.SECONDS
        );
    }

    private void sendHeartbeat() throws OccurenceConstraintException, UnsupportedFeatureException {
        log.debug("ChargePointEmulator (id=%s): sending HeartbeatRequest".formatted(chargePointId));
        ocppClient.send(ocppClientCoreProfile.createHeartbeatRequest()).thenAccept(
            confirmation -> log.debug(
                "ChargePointEmulator (id=%s): HeartbeatConfirmation received: %s".formatted(chargePointId, confirmation)
            )
        );
    }

    private void trySendHeartbeatOrThrow() {
        try {
            sendHeartbeat();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }

    private BootNotificationConfirmation trySendBootNotificationRequestOrThrow() {
        try {
            return sendBootNotificationRequest();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }

    private int validateAndGetHeartbeatIntervalSecondsOrThrow(BootNotificationConfirmation bootNotificationConfirmation) {
        var interval = bootNotificationConfirmation.getInterval();
        if (interval == null || interval < 1) {
            throw unchecked(new IllegalArgumentException(
                "Heartbeat interval is expected to be a positive integer, but was " + interval
            ));
        }
        return interval;
    }
}
