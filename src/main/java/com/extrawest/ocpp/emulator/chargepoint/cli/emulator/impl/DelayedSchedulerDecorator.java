package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class DelayedSchedulerDecorator implements  ChargePointEmulator{
    private ChargePointEmulator chargePointEmulator;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public void start() throws EmulationException {
        Random random = new Random();
        long delay = random.nextLong(1L, 5L);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    new DelayedSchedulerDecorator(chargePointEmulator).start();
                } catch (EmulationException e) {
                    throw new RuntimeException(e);
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
    }

    @Override
    public void stop() {
        chargePointEmulator.stop();
    }
}
