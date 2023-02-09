package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;

public interface RequestSender {

    BootNotificationResponse sendRequest(CentralSystemClient client, BootNotificationRequest request)
        throws EmulationIOException;

    HeartbeatConfirmation sendRequest(CentralSystemClient client, HeartbeatRequest request)
        throws EmulationIOException;

    AuthorizeResponse sendRequest(CentralSystemClient client, AuthorizeRequest request)
        throws EmulationIOException;

    TransactionEventResponse sendRequest(CentralSystemClient client, TransactionEventRequest request)
        throws EmulationIOException;
}
