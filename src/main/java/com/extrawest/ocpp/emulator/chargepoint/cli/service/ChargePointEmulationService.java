package com.extrawest.ocpp.emulator.chargepoint.cli.service;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;

public interface ChargePointEmulationService {

    void runEmulation(ChargePointsEmulationParameters parameters);
}
