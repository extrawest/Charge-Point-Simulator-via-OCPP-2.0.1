package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.ExecutorService;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Validated
public class ExecutorServiceChargePointEmulationService implements ChargePointEmulationService {

    private final ExecutorService executorService;

    @Override
    public void runEmulation(@Valid ChargePointsEmulationParameters parameters) {
        LongStream.range(0, parameters.getChargePointsAmount())
    }
}
