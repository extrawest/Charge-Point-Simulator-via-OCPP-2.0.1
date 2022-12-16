package com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator;

public class EmulationConnectionException extends EmulationException {

    public EmulationConnectionException() {
    }

    public EmulationConnectionException(String message) {
        super(message);
    }

    public EmulationConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulationConnectionException(Throwable cause) {
        super(cause);
    }
}
