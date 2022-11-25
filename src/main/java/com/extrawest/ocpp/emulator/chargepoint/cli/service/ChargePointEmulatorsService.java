package com.extrawest.ocpp.emulator.chargepoint.cli.service;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;

public interface ChargePointEmulatorsService {

    void startEmulation(ChargePointsEmulationParameters parameters);
}
