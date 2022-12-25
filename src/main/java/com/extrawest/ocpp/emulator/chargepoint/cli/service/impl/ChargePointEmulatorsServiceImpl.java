package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.SendBootNotificationAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.CentralSystemConnectAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.StartHeartbeatingAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
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

    private final CentralSystemConnectAction connectAction;

    private final SendBootNotificationAction sendBootNotificationAction;

    private final StartHeartbeatingAction startHeartbeatingAction;

    @Override
    public void startEmulation(@Valid ChargePointsEmulationParameters parameters) throws EmulationException {
        log.info("Trying to start the emulation");
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
        Optional.of(parameters)
            .map(this::createChargePointEmulators)
            .ifPresent(chargePointEmulators -> startChargePointEmulators(chargePointEmulators, parameters));
    }

    private List<ChargePointEmulator> createChargePointEmulators(ChargePointsEmulationParameters parameters) {
        return LongStream.range(0, parameters.getChargePointsCount())
            .parallel()
            .mapToObj(i -> new CreateChargePointParameters(
                parameters.getCentralSystemUrl(), createChargePointIdForIndex(i), chargePointModel, chargePointVendor
            ))
            .map(chargePointEmulatorFactory::createChargePointEmulator)
            .toList();
    }

    private void startChargePointEmulators(
        List<ChargePointEmulator> chargePointEmulators, ChargePointsEmulationParameters parameters
    ) {
        int connectionCountForLogs = parameters.getConnectionCountForLogs();

        log.info("Starting " + chargePointEmulators.size() + " emulators");

        var chargePointsIterator = chargePointEmulators.listIterator();
        while (chargePointsIterator.hasNext()) {
            var index = chargePointsIterator.nextIndex();
            var chargePointEmulator = chargePointsIterator.next();
            Consumer<ChargePointEmulator> startAction = connectAction;
            if (indexNeedsBeLogged(index, connectionCountForLogs)) {
                startAction = startAction.andThen(
                    emulator -> log.info("Currently running " + (index + 1) + " charge points emulators")
                );
            }
            startAction = startAction.andThen(sendBootNotificationAction).andThen(startHeartbeatingAction);
            startAction.accept(chargePointEmulator);
        }
        log.info(chargePointEmulators.size() + " charge points emulators were created");
    }

    private boolean indexNeedsBeLogged(int index, int logEachNth) {
        var startFromOneIndex = index + 1;
        return (startFromOneIndex >= logEachNth) && (startFromOneIndex % logEachNth == 0);
    }

    private String createChargePointIdForIndex(long chargePointIndex) {
        return chargePointIdIndexPrefix + chargePointIndex;
    }
}
