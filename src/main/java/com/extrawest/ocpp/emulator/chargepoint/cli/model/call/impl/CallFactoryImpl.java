package com.extrawest.ocpp.emulator.chargepoint.cli.model.call.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.AuthorizeRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.HeartbeatRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallAction;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
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

    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }
}
