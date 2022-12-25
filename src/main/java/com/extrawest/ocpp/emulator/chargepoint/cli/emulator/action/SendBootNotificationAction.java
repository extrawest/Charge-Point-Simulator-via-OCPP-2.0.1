package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.emptyOptionalException;

@RequiredArgsConstructor
@Component
public class SendBootNotificationAction implements Consumer<ChargePointEmulator> {

    private final CallsSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createBootNotificationRequestFor(chargePointEmulator))
            .map(bootNotificationRequest -> callsSender.sendCall(
                chargePointEmulator.getCentralSystemClient(), bootNotificationRequest
            ))
            .map(CallResult::getPayload)
            .map(BootNotificationConfirmation::getInterval)
            .ifPresentOrElse(
                chargePointEmulator::setHeartbeatInterval,
                () -> {throw emptyOptionalException();}
            );
    }

    private BootNotificationRequest createBootNotificationRequestFor(ChargePointEmulator chargePointEmulator) {
        return new BootNotificationRequest(
            chargePointEmulator.getChargePointModel(), chargePointEmulator.getChargePointVendor()
        );
    }
}
