package com.extrawest.ocpp.emulator.chargepoint.cli.helper;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@FunctionalInterface
public interface ThrowingRunnable extends Runnable {

    @Override
    default void run() {
        try {
            tryRun();
        } catch (Exception e) {
            throw unchecked(e);
        }
    }

    void tryRun() throws Exception;
}
