package com.extrawest.ocpp.emulator.chargepoint.cli.event.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmulationEventListenerImpl implements EmulationEventsListener, ConfigurableMultipleEventHandler {

    private final MultipleEventCounter bootNotificationSentCounter;

    private final MultipleEventCounter heartbeatSentCounter;

    private final MultipleEventCounter authorizeSentCounter;

    private final MultipleEventCounter startTransactionSentCounter;

    private final MultipleEventCounter meterValuesSentCounter;

    @Override
    public void onBootNotificationSent() {
        if (!needHandleEvent(bootNotificationSentCounter)) {
            return;
        }

        log.info("BootNotification sent (total: {})", bootNotificationSentCounter.getCurrentCount());
    }

    @Override
    public void onHeartbeatSent() {
        if (!needHandleEvent(heartbeatSentCounter)) {
            return;
        }

        log.info("Heartbeat sent (total: {})", heartbeatSentCounter.getCurrentCount());
    }

    @Override
    public void onAuthorizeSent() {
        if (!needHandleEvent(authorizeSentCounter)) {
            return;
        }

        log.info("Authorize sent (total: {})", authorizeSentCounter.getCurrentCount());
    }

    @Override
    public void onStartTransactionSent() {
        if (!needHandleEvent(startTransactionSentCounter)) {
            return;
        }

        log.info("StartTransaction sent (total: {})", startTransactionSentCounter.getCurrentCount());
    }

    @Override
    public void onMeterValuesSent() {
        if (!needHandleEvent(meterValuesSentCounter)) {
            return;
        }

        log.info("MeterValues sent (total: {})", meterValuesSentCounter.getCurrentCount());
    }

    @Override
    public void setHandleMultiple(int handleMultiple) {
        bootNotificationSentCounter.setMultiple(handleMultiple);
        heartbeatSentCounter.setMultiple(handleMultiple);
        authorizeSentCounter.setMultiple(handleMultiple);
        startTransactionSentCounter.setMultiple(handleMultiple);
        meterValuesSentCounter.setMultiple(handleMultiple);
    }

    private boolean needHandleEvent(MultipleEventCounter eventCounter) {
        return eventCounter.isEventMultiple();
    }
}
