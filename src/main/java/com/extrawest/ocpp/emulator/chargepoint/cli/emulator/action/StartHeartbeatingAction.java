package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@RequiredArgsConstructor
@Slf4j
public class StartHeartbeatingAction implements Consumer<ChargePointEmulator> {

    private static final HeartbeatRequest HEARTBEAT_REQUEST = new HeartbeatRequest();

    private final CallFactory callFactory;

    private final ScheduledExecutorService scheduledExecutorService;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        try {
            var heartbeatInterval = Optional.of(chargePointEmulator.getHeartbeatInterval())
                .orElseThrow(() -> new IllegalStateException("The emulator does not have heartbeat interval set"));
            var centralSystemClient = chargePointEmulator.getCentralSystemClient();
            scheduledExecutorService.scheduleWithFixedDelay(
                () -> centralSystemClient.sendCall(
                    callFactory.createCallFor(HEARTBEAT_REQUEST), HeartbeatConfirmation.class
                ),
                heartbeatInterval,
                heartbeatInterval,
                TimeUnit.SECONDS
            );
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw unchecked(e);
        }
    }
}
