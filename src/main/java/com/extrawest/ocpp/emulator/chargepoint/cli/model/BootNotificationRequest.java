package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BootNotificationRequest { // TODO: refactor to use CiStirng20

    private final String chargePointModel;

    private final String chargePointVendor;
}
