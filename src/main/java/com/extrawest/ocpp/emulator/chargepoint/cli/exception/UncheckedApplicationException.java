package com.extrawest.ocpp.emulator.chargepoint.cli.exception;

public class UncheckedApplicationException extends RuntimeException {

    public UncheckedApplicationException() {
    }

    public UncheckedApplicationException(String message) {
        super(message);
    }

    public UncheckedApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncheckedApplicationException(Throwable cause) {
        super(cause);
    }
}
