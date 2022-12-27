package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CentralSystemClient;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CallFactoryRequestSender implements RequestSender {

    private final CallFactory callFactory;

    @Override
    public BootNotificationConfirmation sendRequest(
        CentralSystemClient client, BootNotificationRequest request
    ) throws EmulationIOException {
        return extractPayload(
            client.sendCall(callFactory.createCallFor(request), BootNotificationConfirmation.class)
        );
    }

    @Override
    public HeartbeatConfirmation sendRequest(
        CentralSystemClient client, HeartbeatRequest request
    ) throws EmulationIOException {
        return extractPayload(
            client.sendCall(callFactory.createCallFor(request), HeartbeatConfirmation.class)
        );
    }

    @Override
    public AuthorizeConfirmation sendRequest(
        CentralSystemClient client, AuthorizeRequest request
    ) throws EmulationIOException {
        return extractPayload(
            client.sendCall(callFactory.createCallFor(request), AuthorizeConfirmation.class)
        );
    }

    @Override
    public StartTransactionConfirmation sendRequest(
        CentralSystemClient client, StartTransactionRequest request
    ) throws EmulationIOException {
        return extractPayload(
            client.sendCall(callFactory.createCallFor(request), StartTransactionConfirmation.class)
        );
    }

    @Override
    public MeterValuesConfirmation sendRequest(
        CentralSystemClient client, MeterValuesRequest request
    ) throws EmulationIOException {
        return extractPayload(
            client.sendCall(callFactory.createCallFor(request), MeterValuesConfirmation.class)
        );
    }

    private <T> T extractPayload(CallResult<T> callResult) {
        return Optional.of(callResult)
            .map(CallResult::getPayload)
            .orElseThrow(ThrowReadablyUtil::emptyOptionalException);
    }
}
