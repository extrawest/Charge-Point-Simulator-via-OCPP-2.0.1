package com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.ApplicationException;

public class EmulationException extends ApplicationException {

    public EmulationException() {
    }

    public EmulationException(String message) {
        super(message);
    }

    public EmulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulationException(Throwable cause) {
        super(cause);
    }
}
