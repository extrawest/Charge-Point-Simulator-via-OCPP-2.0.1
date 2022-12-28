package com.extrawest.ocpp.emulator.chargepoint.cli.util;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalStateApplicationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.UncheckedApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThrowReadablyUtil {

    public static UncheckedApplicationException unchecked(Throwable cause) {
        return new UncheckedApplicationException(cause);
    }

    public static UncheckedApplicationException unchecked(String message) {
        return new UncheckedApplicationException(message);
    }

    public static UncheckedApplicationException emptyOptionalException() {
        return new UncheckedApplicationException("The Optional was expected to be non-empty");
    }

    public static IllegalStateApplicationException transactionNotStarted(ChargePointEmulator chargePointEmulator) {
        return new IllegalStateApplicationException(
            "There is no transaction associated with the emulator^ " + chargePointEmulator
        );
    }

    public static IllegalStateApplicationException heartbeatNotSet(ChargePointEmulator chargePointEmulator) {
        return new IllegalStateApplicationException(
            "There is no heartbeat set for the emulator: " + chargePointEmulator
        );
    }
}
