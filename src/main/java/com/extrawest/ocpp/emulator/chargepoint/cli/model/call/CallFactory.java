package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.*;

public interface CallFactory {

    Call<BootNotificationRequest> createCallFor(BootNotificationRequest bootNotificationRequest);

    Call<HeartbeatRequest> createCallFor(HeartbeatRequest heartbeatRequest);

    Call<AuthorizeRequest> createCallFor(AuthorizeRequest authorizeRequest);

    Call<StartTransactionRequest> createCallFor(StartTransactionRequest startTransactionRequest);

    Call<MeterValuesRequest> createCallFor(MeterValuesRequest meterValuesRequest);

    Call<StopTransactionRequest> createCallFor(StopTransactionRequest stopTransactionRequest);
}
