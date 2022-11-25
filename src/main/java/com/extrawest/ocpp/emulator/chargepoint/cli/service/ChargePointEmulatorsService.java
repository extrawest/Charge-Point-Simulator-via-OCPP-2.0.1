package com.extrawest.ocpp.emulator.chargepoint.cli.service;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;

import javax.validation.Valid;

public interface ChargePointEmulatorsService {

    void startEmulation(@Valid ChargePointsEmulationParameters parameters);
}
