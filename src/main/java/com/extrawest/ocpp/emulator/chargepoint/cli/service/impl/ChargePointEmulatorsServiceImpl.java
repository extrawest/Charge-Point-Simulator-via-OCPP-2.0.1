package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.factory.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl.ChargePointEmulatorsGroup;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl.DelayedSchedulerDecorator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class ChargePointEmulatorsServiceImpl implements ChargePointEmulatorsService {

    private final ChargePointEmulatorFactory chargePointEmulatorFactory;

    @Value("${ocpp.charge-point.id-index-prefix:CP}")
    private final String chargePointIdIndexPrefix;

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

    private void createAndStartEmulators(ChargePointsEmulationParameters parameters) throws EmulationException {
        var chargePointEmulationGroup = LongStream.range(0, parameters.getChargePointsCount())
            .parallel()
            .mapToObj(
                i -> new CreateChargePointParameters(parameters.getCentralSystemUrl(), createChargePointIdForIndex(i))
            )
            .map(chargePointEmulatorFactory::createChargePointEmulator)
            .collect(Collectors.collectingAndThen(Collectors.toList(), ChargePointEmulatorsGroup::new));
        new DelayedSchedulerDecorator(chargePointEmulationGroup).start();
    }

    private String createChargePointIdForIndex(long chargePointIndex) {
        return chargePointIdIndexPrefix + chargePointIndex;
    }
}
