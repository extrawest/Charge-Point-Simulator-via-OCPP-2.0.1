package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;

public interface CallsSender { // TODO: make the methods type-consistent (a request should produce a response, not a CallResult, thus not CallsSender, but RequestsSender)

    CallResult<BootNotificationConfirmation> sendCall(CentralSystemClient client, BootNotificationRequest request)
        throws EmulationIOException;

    CallResult<HeartbeatConfirmation> sendCall(CentralSystemClient client, HeartbeatRequest request)
        throws EmulationIOException;

    CallResult<AuthorizeConfirmation> sendCall(CentralSystemClient client, AuthorizeRequest request)
        throws EmulationIOException;

    CallResult<StartTransactionConfirmation> sendCall(CentralSystemClient client, StartTransactionRequest request)
        throws EmulationIOException;

    CallResult<MeterValuesConfirmation> sendCall(CentralSystemClient client, MeterValuesRequest request)
        throws EmulationIOException;
}
