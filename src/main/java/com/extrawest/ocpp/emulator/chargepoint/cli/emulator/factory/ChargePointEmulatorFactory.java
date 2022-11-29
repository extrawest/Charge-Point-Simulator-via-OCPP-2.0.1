package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.factory;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;

public interface ChargePointEmulatorFactory {

    ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters);
}
