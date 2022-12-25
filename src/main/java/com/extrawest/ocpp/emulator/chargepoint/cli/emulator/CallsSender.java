package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;

public interface CallsSender {

    CallResult<BootNotificationConfirmation> sendCall(CentralSystemClient client, BootNotificationRequest request);

    CallResult<HeartbeatConfirmation> sendCall(CentralSystemClient client, HeartbeatRequest request);
}
