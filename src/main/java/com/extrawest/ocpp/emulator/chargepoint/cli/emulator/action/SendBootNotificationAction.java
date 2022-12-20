package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.emptyOptionalException;

@RequiredArgsConstructor
public class SendBootNotificationAction implements Consumer<ChargePointEmulator> {

    private final CallFactory callFactory;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var centralSystemClient = chargePointEmulator.getCentralSystemClient();
        var bootNotificationCall = callFactory.createCallFor(new BootNotificationRequest(
            chargePointEmulator.getChargePointModel(), chargePointEmulator.getChargePointVendor()
        ));
        Optional.of(centralSystemClient.sendCall(bootNotificationCall, BootNotificationConfirmation.class))
            .map(CallResult::getPayload)
            .map(BootNotificationConfirmation::getInterval)
            .ifPresentOrElse(
                chargePointEmulator::setHeartbeatInterval,
                () -> {throw emptyOptionalException();}
            );
    }
}
