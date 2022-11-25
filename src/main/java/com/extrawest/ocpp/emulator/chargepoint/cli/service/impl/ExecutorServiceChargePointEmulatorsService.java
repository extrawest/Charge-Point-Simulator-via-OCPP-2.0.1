package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.factory.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Validated
public class ExecutorServiceChargePointEmulatorsService implements ChargePointEmulatorsService {

    private final ChargePointEmulatorFactory chargePointEmulatorFactory;

    @Value("${ocpp.charge-point.id-index-prefix:CP}")
    private final String chargePointIdIndexPrefix;

    @Override
    public void startEmulation(@Valid ChargePointsEmulationParameters parameters) {
        LongStream.range(0, parameters.getChargePointsCount())
            .parallel()
            .mapToObj(
                i -> new CreateChargePointParameters(parameters.getCentralSystemUrl(), createChargePointIdForIndex(i))
            )
            .map(chargePointEmulatorFactory::createChargePointEmulator)
            .forEach(ChargePointEmulator::start);
    }

    private String createChargePointIdForIndex(long chargePointIndex) {
        return chargePointIdIndexPrefix + chargePointIndex;
    }
}
