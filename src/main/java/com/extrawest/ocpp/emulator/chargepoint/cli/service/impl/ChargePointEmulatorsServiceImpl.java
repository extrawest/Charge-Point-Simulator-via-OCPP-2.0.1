package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.SendBootNotificationAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.CentralSystemConnectAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.StartHeartbeatingAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class ChargePointEmulatorsServiceImpl implements ChargePointEmulatorsService {

    private final ChargePointEmulatorFactory chargePointEmulatorFactory;

    @Value("${ocpp.charge-point.id-index-prefix:CP}")
    private final String chargePointIdIndexPrefix;

    @Value("${ocpp.charge-point.boot-notification.chargePointModel}")
    private final String chargePointModel;

    @Value("${ocpp.charge-point.boot-notification.chargePointVendor}")
    private final String chargePointVendor;

    private final CallFactory callFactory;

    private final ScheduledExecutorService scheduledExecutorService;

    @Override
    public void startEmulation(@Valid ChargePointsEmulationParameters parameters) throws EmulationException {
        tryCreateAndStartEmulatorsOrThrow(parameters);
    }

    private void tryCreateAndStartEmulatorsOrThrow(ChargePointsEmulationParameters parameters)
        throws EmulationException {
        try {
            createAndStartEmulators(parameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new EmulationException(e);
        }
    }

    private void createAndStartEmulators(ChargePointsEmulationParameters parameters) {
        var connectAction = new CentralSystemConnectAction();
        var sendBootNotificationAction = new SendBootNotificationAction(callFactory);
        var startHeartbeatingAction = new StartHeartbeatingAction(callFactory, scheduledExecutorService);

        LongStream.range(0, parameters.getChargePointsCount())
            .parallel()
            .mapToObj(i -> new CreateChargePointParameters(
                parameters.getCentralSystemUrl(), createChargePointIdForIndex(i), chargePointModel, chargePointVendor
            ))
            .map(chargePointEmulatorFactory::createChargePointEmulator)
            .forEach(chargePointEmulator -> connectAction
                .andThen(sendBootNotificationAction)
                .andThen(startHeartbeatingAction)
                .accept(chargePointEmulator)
            );
    }

    private String createChargePointIdForIndex(long chargePointIndex) {
        return chargePointIdIndexPrefix + chargePointIndex;
    }
}
