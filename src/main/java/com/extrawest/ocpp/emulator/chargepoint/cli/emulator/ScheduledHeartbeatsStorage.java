package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import java.util.concurrent.ScheduledFuture;

public interface ScheduledHeartbeatsStorage {

    void store(ChargePointEmulator chargePointEmulator, ScheduledFuture<?> heartbeatingFuture);

    ScheduledFuture<?> remove(ChargePointEmulator chargePointEmulator);
}
