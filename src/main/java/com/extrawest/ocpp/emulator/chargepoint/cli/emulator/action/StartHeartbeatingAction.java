package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.heartbeatNotSet;

@RequiredArgsConstructor
@Slf4j
@Component
public class StartHeartbeatingAction implements Consumer<ChargePointEmulator> {

    private static final HeartbeatRequest HEARTBEAT_REQUEST = new HeartbeatRequest();

    private final ScheduledExecutorService scheduledExecutorService;

    private final RequestSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var heartbeatInterval = Optional.ofNullable(chargePointEmulator.getHeartbeatInterval())
            .orElseThrow(() -> heartbeatNotSet(chargePointEmulator));
        scheduledExecutorService.scheduleWithFixedDelay(
            (ThrowingRunnable)
                () -> callsSender.sendRequest(chargePointEmulator.getCentralSystemClient(), HEARTBEAT_REQUEST),
            heartbeatInterval.toSeconds(),
            heartbeatInterval.toSeconds(),
            TimeUnit.SECONDS
        );
    }
}
