package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChargePointEmulator {

    private final CentralSystemClient centralSystemClient;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final String chargePointId;

    private final String centralSystemUrl;

    private Integer heartbeatInterval;
}
