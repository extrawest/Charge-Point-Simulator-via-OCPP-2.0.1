package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CentralSystemClient;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CallFactoryCallSender implements CallsSender {

    private final CallFactory callFactory;

    @Override
    public CallResult<BootNotificationConfirmation> sendCall(
        CentralSystemClient client, BootNotificationRequest request
    ) throws EmulationIOException {
        return client.sendCall(callFactory.createCallFor(request), BootNotificationConfirmation.class);
    }

    @Override
    public CallResult<HeartbeatConfirmation> sendCall(
        CentralSystemClient client, HeartbeatRequest request
    ) throws EmulationIOException {
        return client.sendCall(callFactory.createCallFor(request), HeartbeatConfirmation.class);
    }

    @Override
    public CallResult<AuthorizeConfirmation> sendCall(
        CentralSystemClient client, AuthorizeRequest request
    ) throws EmulationIOException {
        return client.sendCall(callFactory.createCallFor(request), AuthorizeConfirmation.class);
    }

    @Override
    public CallResult<StartTransactionConfirmation> sendCall(
        CentralSystemClient client, StartTransactionRequest request
    ) throws EmulationIOException {
        return client.sendCall(callFactory.createCallFor(request), StartTransactionConfirmation.class);
    }

    @Override
    public CallResult<MeterValuesConfirmation> sendCall(
        CentralSystemClient client, MeterValuesRequest request
    ) throws EmulationIOException {
        return client.sendCall(callFactory.createCallFor(request), MeterValuesConfirmation.class);
    }
}
