package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ScheduledHeartbeatsStorage;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalStateApplicationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;

@Component
public class ScheduledHeartbeatsStorageImpl implements ScheduledHeartbeatsStorage {

    private final Map<ChargePointEmulator, ScheduledFuture<?>> scheduledHeartbeats = new HashMap<>();

    @Override
    public void store(ChargePointEmulator chargePointEmulator, ScheduledFuture<?> heartbeatingFuture) {
        if (scheduledHeartbeats.containsKey(chargePointEmulator)) {
            throw new IllegalStateApplicationException(
                "The storage already contains a scheduled heartbeating future for the emulator"
            );
        }
        scheduledHeartbeats.put(chargePointEmulator, heartbeatingFuture);
    }

    @Override
    public ScheduledFuture<?> remove(ChargePointEmulator chargePointEmulator) {
        return Optional.of(chargePointEmulator)
            .map(scheduledHeartbeats::remove)
            .orElseThrow(() -> new IllegalStateApplicationException(
                "There is no heartbeating scheduled future in this storage. Consider storing"
            ));
    }
}
