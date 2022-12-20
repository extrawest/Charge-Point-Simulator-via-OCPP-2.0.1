package com.extrawest.ocpp.emulator.chargepoint.cli.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.function.Supplier;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ConcurrencyUtil {

    public static <T> T acquireGetRelease(Semaphore semaphore, Supplier<T> supplier) {
        try {
            semaphore.acquire(1);
            return supplier.get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw unchecked(e);
        } finally {
            semaphore.release();
        }
    }
}
