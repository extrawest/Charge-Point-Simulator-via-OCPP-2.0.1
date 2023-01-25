package com.extrawest.ocpp.emulator.chargepoint.cli.model.call.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CallFactoryImpl implements CallFactory {

    @Override
    public Call<BootNotificationRequest> createCallFor(BootNotificationRequest bootNotificationRequest) {
        return new Call<>(generateUniqueId(), CallAction.BootNotification, bootNotificationRequest);
    }

    @Override
    public Call<HeartbeatRequest> createCallFor(HeartbeatRequest heartbeatRequest) {
        return new Call<>(generateUniqueId(), CallAction.Heartbeat, heartbeatRequest);
    }

    @Override
    public Call<AuthorizeRequest> createCallFor(AuthorizeRequest authorizeRequest) {
        return new Call<>(generateUniqueId(), CallAction.Authorize, authorizeRequest);
    }

    @Override
    public Call<StartTransactionRequest> createCallFor(StartTransactionRequest startTransactionRequest) {
        return new Call<>(generateUniqueId(), CallAction.StartTransaction, startTransactionRequest);
    }

    @Override
    public Call<TransactionEventRequest> createCallFor(TransactionEventRequest meterValuesRequest) {
        return new Call<>(generateUniqueId(), CallAction.MeterValues, meterValuesRequest);
    }

    @Override
    public Call<StopTransactionRequest> createCallFor(StopTransactionRequest stopTransactionRequest) {
        return new Call<>(generateUniqueId(), CallAction.StopTransaction, stopTransactionRequest);
    }

    @Override
    public Call<RequestStartTransactionRequest> createCallFor(RequestStartTransactionRequest requestStartTransactionRequest) {
        return new Call<>(generateUniqueId(), CallAction.RequestStartTransaction, requestStartTransactionRequest);
    }

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
