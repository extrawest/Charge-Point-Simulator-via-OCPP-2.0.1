package com.extrawest.ocpp.emulator.chargepoint.cli.helper;

import java.util.function.Function;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@FunctionalInterface
public interface ThrowingFunction<T, R> extends Function<T, R> {

    @Override
    default R apply(T argument) {
        try {
            return tryApply(argument);
        } catch (Exception e) {
            throw unchecked(e);
        }
    }

    R tryApply(T argument) throws Exception;
}
