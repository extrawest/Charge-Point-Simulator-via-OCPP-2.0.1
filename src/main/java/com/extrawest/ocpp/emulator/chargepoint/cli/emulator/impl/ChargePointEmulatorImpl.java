package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChargePointEmulatorImpl implements ChargePointEmulator {

    private final ChargePointEmulatorStrategy emulationStrategy;

    @Override
    public void start() {
        emulationStrategy.startEmulation();
    }
}
