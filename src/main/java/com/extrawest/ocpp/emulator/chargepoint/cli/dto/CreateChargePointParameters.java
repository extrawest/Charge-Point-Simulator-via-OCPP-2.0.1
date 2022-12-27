package com.extrawest.ocpp.emulator.chargepoint.cli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateChargePointParameters { // TODO: move non-variable parameters to ChargePointFactory

    private final String centralSystemUrl;

    private final String chargePointId;

    private final String chargePointModel;

    private final String chargePointVendor;
}
