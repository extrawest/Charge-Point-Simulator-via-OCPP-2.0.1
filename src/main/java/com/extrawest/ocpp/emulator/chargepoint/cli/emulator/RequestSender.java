package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;

public interface RequestSender {

    BootNotificationConfirmation sendRequest(CentralSystemClient client, BootNotificationRequest request)
        throws EmulationIOException;

    HeartbeatConfirmation sendRequest(CentralSystemClient client, HeartbeatRequest request)
        throws EmulationIOException;

    AuthorizeConfirmation sendRequest(CentralSystemClient client, AuthorizeRequest request)
        throws EmulationIOException;

    StartTransactionConfirmation sendRequest(CentralSystemClient client, StartTransactionRequest request)
        throws EmulationIOException;

    MeterValuesConfirmation sendRequest(CentralSystemClient client, MeterValuesRequest request)
        throws EmulationIOException;
}
