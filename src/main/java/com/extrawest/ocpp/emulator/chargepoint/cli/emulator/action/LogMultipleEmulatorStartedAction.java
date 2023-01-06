package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public class LogMultipleEmulatorStartedAction implements Consumer<ChargePointEmulator> {

    private final int logMultipleForLogs;

    private int currentLogRequestCounter;

    private final Object currentLogRequestCounterMonitor = new Object();

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        synchronized (currentLogRequestCounterMonitor) {
            currentLogRequestCounter++;

            if (indexNeedsBeLogged(currentLogRequestCounter, logMultipleForLogs)) {
                log.info("Currently running " + currentLogRequestCounter + " charge points emulators");
            }
        }
    }

    private boolean indexNeedsBeLogged(int logRequestNumber, int logMultiple) {
        return (logRequestNumber >= logMultiple) && (logRequestNumber % logMultiple == 0);
    }
}
