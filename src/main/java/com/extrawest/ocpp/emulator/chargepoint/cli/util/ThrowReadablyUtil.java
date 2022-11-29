package com.extrawest.ocpp.emulator.chargepoint.cli.util;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.UncheckedApplicationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.CentralSystemUnavailableException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ThrowReadablyUtil {

    public static UncheckedApplicationException unchecked(Throwable cause) {
        return new UncheckedApplicationException(cause);
    }

    public static UncheckedApplicationException emptyOptionalException() {
        return new UncheckedApplicationException("The Optional was expected to be non-empty");
    }

    public static CentralSystemUnavailableException unableToConnect(String centralSystemUrl) {
        return new CentralSystemUnavailableException(
            "Can not connect to the central system (url = %s)".formatted(centralSystemUrl)
        );
    }
}
