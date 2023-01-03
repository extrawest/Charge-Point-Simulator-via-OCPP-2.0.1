package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.client.JettyUpgradeListener;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ChargePointEmulatorFactoryImpl implements ChargePointEmulatorFactory {

    private final ObjectMapper objectMapper;

    private final WebSocketClient webSocketClient;

    private final JettyUpgradeListener jettyUpgradeListener;

    @Value("${ocpp.charge-point.meter-values.send-interval:PT10S}")
    @NonNull
    private final Duration sendMeterValuesInterval;

    @Value("${ocpp.charge-point.boot-notification.chargePointModel}")
    private final String chargePointModel;

    @Value("${ocpp.charge-point.boot-notification.chargePointVendor}")
    private final String chargePointVendor;

    @Override
    public ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters) {
        return ChargePointEmulator.builder()
            .centralSystemClient(new JettyWebsocketClient(objectMapper, webSocketClient, jettyUpgradeListener))
            .chargePointModel(chargePointModel)
            .chargePointVendor(chargePointVendor)
            .chargePointId(createChargePointParameters.getChargePointId())
            .centralSystemUrl(createChargePointParameters.getCentralSystemUrl())
            .sendMeterValuesInterval(sendMeterValuesInterval)
            .build();
    }
}
