package com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator;

public class EmulationIOException extends EmulationException {

    public EmulationIOException() {
    }

    public EmulationIOException(String message) {
        super(message);
    }

    public EmulationIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulationIOException(Throwable cause) {
        super(cause);
    }
}
