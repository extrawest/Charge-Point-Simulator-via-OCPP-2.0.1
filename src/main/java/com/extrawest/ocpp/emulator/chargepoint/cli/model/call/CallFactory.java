package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.AuthorizeRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;

public interface CallFactory {

    Call<BootNotificationRequest> createCallFor(BootNotificationRequest bootNotificationRequest);

    Call<HeartbeatRequest> createCallFor(HeartbeatRequest heartbeatRequest);

    Call<AuthorizeRequest> createCallFor(AuthorizeRequest authorizeRequest);
}
