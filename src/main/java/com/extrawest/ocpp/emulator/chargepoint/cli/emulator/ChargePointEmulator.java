package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTagInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Duration;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ChargePointEmulator {

    private final CentralSystemClient centralSystemClient;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final String chargePointId;

    private final String centralSystemUrl;

    private Integer heartbeatInterval; // TODO: refactor to use Duration instead

    private IdTagInfo authorizeIdTagInfo;

    private int currentMeterValue;

    private Integer currentTransactionId;

    private Duration sendMeterValuesInterval;
}
