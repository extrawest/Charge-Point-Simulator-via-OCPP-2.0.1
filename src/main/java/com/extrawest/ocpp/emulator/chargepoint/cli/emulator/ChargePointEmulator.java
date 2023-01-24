package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTagInfo;
import lombok.*;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(of = "chargePointId")
public class ChargePointEmulator {

    private final CentralSystemClient centralSystemClient;

    private final String chargePointModel;

    private final String chargePointVendor;

    private final String chargePointId;

    private final String centralSystemUrl;

    private Duration heartbeatInterval;

    private IdTagInfo authorizeIdTagInfo;

    private AtomicInteger currentMeterValue;

    private UUID currentTransactionId;

    private Duration sendMeterValuesInterval;
}
