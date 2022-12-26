package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.*;

public interface CallFactory {

    Call<BootNotificationRequest> createCallFor(BootNotificationRequest bootNotificationRequest);

    Call<HeartbeatRequest> createCallFor(HeartbeatRequest heartbeatRequest);

    Call<AuthorizeRequest> createCallFor(AuthorizeRequest authorizeRequest);

    Call<StartTransactionRequest> createCallFor(StartTransactionRequest startTransactionRequest);
}
