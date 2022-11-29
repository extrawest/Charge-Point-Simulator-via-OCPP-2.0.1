package com.extrawest.ocpp.emulator.chargepoint.cli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateChargePointParameters {

    private final String centralSystemUrl;

    private final String chargePointId;
}
