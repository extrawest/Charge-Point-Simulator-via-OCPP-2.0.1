package com.extrawest.ocpp.emulator.chargepoint.cli.util;

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
}
