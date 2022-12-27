package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;

public interface CallsSender {

    BootNotificationConfirmation sendCall(CentralSystemClient client, BootNotificationRequest request)
        throws EmulationIOException;

    HeartbeatConfirmation sendCall(CentralSystemClient client, HeartbeatRequest request)
        throws EmulationIOException;

    AuthorizeConfirmation sendCall(CentralSystemClient client, AuthorizeRequest request)
        throws EmulationIOException;

    StartTransactionConfirmation sendCall(CentralSystemClient client, StartTransactionRequest request)
        throws EmulationIOException;

    MeterValuesConfirmation sendCall(CentralSystemClient client, MeterValuesRequest request)
        throws EmulationIOException;
}
