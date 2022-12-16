package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;

public interface ChargePointEmulatorFactory {

    ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters);
}
