package com.extrawest.ocpp.emulator.chargepoint.cli.exception;

public class IllegalStateApplicationException extends UncheckedApplicationException {

    public IllegalStateApplicationException() {
    }

    public IllegalStateApplicationException(String message) {
        super(message);
    }

    public IllegalStateApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalStateApplicationException(Throwable cause) {
        super(cause);
    }
}
