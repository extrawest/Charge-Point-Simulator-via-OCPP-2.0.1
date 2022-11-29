package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class ChargePointEmulatorsGroup implements ChargePointEmulator {

    private final Collection<ChargePointEmulator> emulators;

    @Override
    public void start() throws EmulationException {
        var startedEmulators = new ArrayList<ChargePointEmulator>(emulators.size());
        try {
            for (var chargePointEmulator : emulators) {
                new DelayedSchedulerDecorator(chargePointEmulator).start();
                startedEmulators.add(chargePointEmulator);
            }
        } catch (EmulationException e) {
            log.error(e.getMessage(), e);
            stopChargePointEmulators(startedEmulators);
            throw new EmulationException(e);
        }
    }

    @Override
    public void stop() {
        stopChargePointEmulators(this.emulators);
    }

    private void stopChargePointEmulators(Collection<ChargePointEmulator> chargePointEmulators) {
        chargePointEmulators.forEach(ChargePointEmulator::stop);
    }
}
