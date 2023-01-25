package com.extrawest.ocpp.emulator.chargepoint.cli.event;

public interface EmulationEventsListener {

    void onBootNotificationSent();

    void onHeartbeatSent();

    void onAuthorizeSent();

    void onStartTransactionSent();

    void onMeterValuesSent();

    void onRequestStartTransactionSent();
}
