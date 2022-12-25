package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;
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

    private final ScheduledExecutorService scheduledExecutorService;

    private final CallsSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var heartbeatInterval = Optional.of(chargePointEmulator.getHeartbeatInterval()).orElseThrow(
            () -> unchecked(new IllegalStateException("The emulator does not have heartbeat interval set"))
        );
        scheduledExecutorService.scheduleWithFixedDelay(
            () -> callsSender.sendCall(chargePointEmulator.getCentralSystemClient(), HEARTBEAT_REQUEST),
            heartbeatInterval,
            heartbeatInterval,
            TimeUnit.SECONDS
        );
    }
}
