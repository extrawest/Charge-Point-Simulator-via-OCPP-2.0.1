package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.Executor;

@RequiredArgsConstructor
public class ChargePointEmulatorAsyncDecorator implements ChargePointEmulator {

    private final Executor executor;

    private final ChargePointEmulator decorated;

    @Override
    public void start() {
        executor.execute(decorated::start);
    }
}
