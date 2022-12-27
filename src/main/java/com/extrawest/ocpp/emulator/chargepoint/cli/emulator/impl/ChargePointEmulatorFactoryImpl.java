package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class ChargePointEmulatorFactoryImpl implements ChargePointEmulatorFactory {

    private final ObjectMapper objectMapper;

    private final WebSocketClient webSocketClient;

    @Value("${ocpp.charge-point.meter-values.send-interval:PT10S}")
    @NonNull
    private final Duration sendMeterValuesInterval;

    @Override
    public ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters) {
        var emulator = new ChargePointEmulator(
            new JettyWebsocketClient(objectMapper, webSocketClient),
            createChargePointParameters.getChargePointModel(),
            createChargePointParameters.getChargePointVendor(),
            createChargePointParameters.getChargePointId(),
            createChargePointParameters.getCentralSystemUrl()
        );
        emulator.setSendMeterValuesInterval(sendMeterValuesInterval);
        return emulator;
    }
}
