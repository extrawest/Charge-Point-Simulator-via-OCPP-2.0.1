package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalStateApplicationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledHeartbeatsStorage {

    private final Map<ChargePointEmulator, ScheduledFuture<?>> scheduledHeartbeats = new HashMap<>();

    public void store(ChargePointEmulator chargePointEmulator, ScheduledFuture<?> heartbeatingFuture) {
        if (scheduledHeartbeats.containsKey(chargePointEmulator)) {
            throw new IllegalStateApplicationException(
                "The storage already contains a scheduled heartbeating future for the emulator"
            );
        }
        scheduledHeartbeats.put(chargePointEmulator, heartbeatingFuture);
    }

    public ScheduledFuture<?> remove(ChargePointEmulator chargePointEmulator) {
        return Optional.of(chargePointEmulator)
            .map(scheduledHeartbeats::get)
            .orElseThrow(() -> new IllegalStateApplicationException(
                "There is no heartbeating scheduled future in this storage. Consider storing"
            ));
    }
}
