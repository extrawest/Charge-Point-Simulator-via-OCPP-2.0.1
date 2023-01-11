package com.extrawest.ocpp.emulator.chargepoint.cli.event.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebsocketEventListenerImpl implements WebsocketEventListener, ConfigurableMultipleEventHandler {

    private final MultipleEventCounter connectionClosedCounter;

    private final AtomicInteger failedConnectionsCounter = new AtomicInteger(0);

    @Override
    public void onConnectionFailed() {
        log.error("Failed to connect (total: {})", failedConnectionsCounter.incrementAndGet());
    }

    @Override
    public void onConnectionClosed() {
        if (!connectionClosedCounter.isEventMultiple()) {
            return;
        }

        log.warn("WS connection closed (total: {})", connectionClosedCounter.getCurrentCount());
    }

    @Override
    public void setHandleMultiple(int handleMultiple) {
        connectionClosedCounter.setMultiple(handleMultiple);
    }
}
