package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class StopHeartbeatingAction implements Consumer<ChargePointEmulator> {

    private final ScheduledHeartbeatsStorage scheduledHeartbeatsStorage;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var scheduledHeartbeating = scheduledHeartbeatsStorage.remove(chargePointEmulator);
        scheduledHeartbeating.cancel(true);
    }
}
