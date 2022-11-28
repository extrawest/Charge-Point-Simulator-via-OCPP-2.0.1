package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.factory.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.factory.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl.ChargePointEmulatorImpl;
import com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime.IClientAPIFactory;
import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;

@Component
@RequiredArgsConstructor
public class ChargePointEmulatorAsyncDecoratorFactory implements ChargePointEmulatorFactory {

    private final ScheduledExecutorService scheduledExecutorService;

    private final IClientAPIFactory ocppClientFactory;

    private final ClientEvents ocppClientEvents;

    private final ClientCoreProfile ocppClientCoreProfile;

    @Value("${ocpp.charge-point.boot-notification.chargePointModel}")
    private final String chargePointModel;

    @Value("${ocpp.charge-point.boot-notification.chargePointVendor}")
    private final String chargePointVendor;

    @Override
    public ChargePointEmulator createChargePointEmulator(CreateChargePointParameters createChargePointParameters) {
        return new ChargePointEmulatorImpl(
            createChargePointParameters.getCentralSystemUrl(),
            ocppClientFactory.createNewClientApi(),
            ocppClientEvents,
            ocppClientCoreProfile,
            scheduledExecutorService,
            chargePointModel,
            chargePointVendor,
            createChargePointParameters.getChargePointId()
        );
    }
}
