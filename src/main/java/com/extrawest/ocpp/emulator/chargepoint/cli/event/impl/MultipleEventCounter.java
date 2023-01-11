package com.extrawest.ocpp.emulator.chargepoint.cli.event.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class MultipleEventCounter {

    private final AtomicInteger handleMultiple = new AtomicInteger(1);

    private final AtomicInteger eventsCounter = new AtomicInteger(0);

    public boolean isEventMultiple() {
        int currentEventNumber = eventsCounter.incrementAndGet();
        int currentMultiple = handleMultiple.get();
        return (currentEventNumber % currentMultiple == 0) && (currentEventNumber >= currentMultiple);
    }

    public int getCurrentCount() {
        return eventsCounter.get();
    }

    public void setMultiple(int newHandleMultiple) {
        this.handleMultiple.set(newHandleMultiple);
    }
}
