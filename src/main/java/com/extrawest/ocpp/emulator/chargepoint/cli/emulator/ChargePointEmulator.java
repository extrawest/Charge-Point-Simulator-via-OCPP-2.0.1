package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTagInfo;
import lombok.*;

import java.time.Duration;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ChargePointEmulator {

    private final CentralSystemClient centralSystemClient;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final String chargePointId;

    private final String centralSystemUrl;

    private Duration heartbeatInterval;

    private IdTagInfo authorizeIdTagInfo;

    private int currentMeterValue;

    private Integer currentTransactionId;

    private Duration sendMeterValuesInterval;
}
