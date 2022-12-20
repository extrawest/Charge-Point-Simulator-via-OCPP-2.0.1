package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.constant.PicocliConstants;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Setter
public class ChargePointEmulatorsGroup implements ChargePointEmulator {

    private final Collection<ChargePointEmulator> emulators;
    @Value("${ocpp.charge-point.logs-count}")
    private int logsCount;

    @Override
    public void start() throws EmulationException {
        var startedEmulators = new ArrayList<ChargePointEmulator>(emulators.size());
        log.info("Starting " + emulators.size() + " emulators");
        try {
            for (var chargePointEmulator : emulators) {
                chargePointEmulator.start();
                startedEmulators.add(chargePointEmulator);
                if (logsCount != 0) {
                    if (startedEmulators.size() % logsCount == 0) {
                        log.info("Currently running " + startedEmulators.size() + " charge points emulators" );
                    }
                } else if (startedEmulators.size() % PicocliConstants.DEFAULT_LOGS_RANGE == 0) {
                    log.info("Currently running " + startedEmulators.size() + " charge points emulators" );
                }

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
        log.info("Stopping " + chargePointEmulators.size() + " charge points emulators");
        chargePointEmulators.forEach(ChargePointEmulator::stop);
        log.info("All charge points emulators was stopped");
    }
}
