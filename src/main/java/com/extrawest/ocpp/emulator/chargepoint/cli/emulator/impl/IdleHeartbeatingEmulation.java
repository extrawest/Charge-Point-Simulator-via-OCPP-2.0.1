package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorStrategy;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.IClientAPI;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.core.BootNotificationConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@AllArgsConstructor
@Builder
@Slf4j
public class IdleHeartbeatingEmulation implements ChargePointEmulatorStrategy {

    private final String centralSystemUri;

    private final IClientAPI ocppClient;

    private final ClientEvents ocppClientEvents;

    private final ClientCoreProfile ocppClientCoreProfile;

    private final ScheduledExecutorService scheduledExecutorService;

    @Override
    public void startEmulation() {
        ocppClient.connect(centralSystemUri, ocppClientEvents);
        var heartbeatIntervalSeconds = Optional.of(sendBootNotificationRequest())
            .map(this::validateAndGetHeartbeatIntervalSecondsOrThrow)
            .orElseThrow(ThrowReadablyUtil::emptyOptionalException);
        scheduleHeartbeat(heartbeatIntervalSeconds);
    }

    private BootNotificationConfirmation sendBootNotificationRequest() {

    }

    private void scheduleHeartbeat(int heartbeatIntervalSeconds) {
        scheduledExecutorService.schedule(this::trySendHeartbeatOrThrow, heartbeatIntervalSeconds, TimeUnit.SECONDS);
    }

    private void sendHeartbeat() throws OccurenceConstraintException, UnsupportedFeatureException {
        ocppClient.send(ocppClientCoreProfile.createHeartbeatRequest());
    }

    private void trySendHeartbeatOrThrow() {
        try {
            sendHeartbeat();
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
