package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class ChargePointEmulatorAsyncDecoratorFactory implements ChargePointEmulatorFactory { // TODO: rename

    private final ScheduledExecutorService scheduledExecutorService;

    @Value("${ocpp.charge-point.boot-notification.chargePointModel}")
    private final String chargePointModel;

    @Value("${ocpp.charge-point.boot-notification.chargePointVendor}")
    private final String chargePointVendor;

    private final ObjectMapper objectMapper;

    private final WebSocketClient webSocketClient;

    private final CallFactory callFactory;

    @Override
    public ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters) {
        return new ChargePointEmulatorImpl(
            callFactory,
            createChargePointParameters.getCentralSystemUrl(),
            createChargePointParameters.getChargePointId(),
            scheduledExecutorService,
            chargePointModel,
            chargePointVendor,
            new JettyWebsocketClient(objectMapper, webSocketClient)
        );
    }
}
