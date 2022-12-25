package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChargePointEmulatorFactoryImpl implements ChargePointEmulatorFactory {

    private final ObjectMapper objectMapper;

    private final WebSocketClient webSocketClient;

    @Override
    public ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters) {
        return new ChargePointEmulator(
            new JettyWebsocketClient(objectMapper, webSocketClient),
            createChargePointParameters.getChargePointModel(),
            createChargePointParameters.getChargePointVendor(),
            createChargePointParameters.getChargePointId(),
            createChargePointParameters.getCentralSystemUrl()
        );
    }
}
