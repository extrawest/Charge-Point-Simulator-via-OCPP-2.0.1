package com.extrawest.ocpp.emulator.chargepoint.cli.event;

public interface WebsocketEventListener {

    void onConnectionFailed();

    void onConnectionClosed();
}
