package com.extrawest.ocpp.emulator.chargepoint.cli.exception;

public class IllegalArgumentApplicationException extends UncheckedApplicationException {

    public IllegalArgumentApplicationException() {
    }

    public IllegalArgumentApplicationException(String message) {
        super(message);
    }

    public IllegalArgumentApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentApplicationException(Throwable cause) {
        super(cause);
    }
}
