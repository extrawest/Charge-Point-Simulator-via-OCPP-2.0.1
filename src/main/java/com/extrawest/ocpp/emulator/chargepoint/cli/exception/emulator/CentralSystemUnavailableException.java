package com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator;

public class CentralSystemUnavailableException extends EmulationException{
    public CentralSystemUnavailableException() {
    }

    public CentralSystemUnavailableException(String message) {
        super(message);
    }

    public CentralSystemUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public CentralSystemUnavailableException(Throwable cause) {
        super(cause);
    }
}
