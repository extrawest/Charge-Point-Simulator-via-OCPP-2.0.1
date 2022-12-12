package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.Getter;

@Getter
public class BootNotificationRequest {

    private final String chargePointModel;

    private final String chargePointVendor;

    public BootNotificationRequest(String chargePointModel, String chargePointVendor) {
        this.chargePointModel = chargePointModel;
        this.chargePointVendor = chargePointVendor;
    }
}
