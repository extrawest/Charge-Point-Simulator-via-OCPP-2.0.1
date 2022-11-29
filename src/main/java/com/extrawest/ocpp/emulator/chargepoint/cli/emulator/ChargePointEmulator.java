package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;

public interface ChargePointEmulator {

    void start() throws EmulationException;

    void stop();
}
