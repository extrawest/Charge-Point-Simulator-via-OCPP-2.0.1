package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@RequiredArgsConstructor
@Slf4j
@Component
public class StartHeartbeatingAction implements Consumer<ChargePointEmulator> {

    private static final HeartbeatRequest HEARTBEAT_REQUEST = new HeartbeatRequest();

    private final ScheduledExecutorService scheduledExecutorService;

    private final CallsSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var heartbeatInterval = chargePointEmulator.getHeartbeatInterval();
        if (heartbeatInterval == null) {
            throw unchecked(new IllegalStateException("The emulator does not have heartbeat interval set"));
        }
        scheduledExecutorService.scheduleWithFixedDelay(
            (ThrowingRunnable)
                () -> callsSender.sendCall(chargePointEmulator.getCentralSystemClient(), HEARTBEAT_REQUEST),
            heartbeatInterval,
            heartbeatInterval,
            TimeUnit.SECONDS
        );
    }
}
