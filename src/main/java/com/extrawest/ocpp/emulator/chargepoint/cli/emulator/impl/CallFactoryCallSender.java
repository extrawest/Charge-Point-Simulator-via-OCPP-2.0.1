package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CentralSystemClient;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CallFactoryCallSender implements CallsSender {

    private final CallFactory callFactory;

    @Override
    public CallResult<BootNotificationConfirmation> sendCall(
        CentralSystemClient client, BootNotificationRequest request
    ) {
        return client.sendCall(callFactory.createCallFor(request), BootNotificationConfirmation.class);
    }

    @Override
    public CallResult<HeartbeatConfirmation> sendCall(CentralSystemClient client, HeartbeatRequest request) {
        return client.sendCall(callFactory.createCallFor(request), HeartbeatConfirmation.class);
    }
}
