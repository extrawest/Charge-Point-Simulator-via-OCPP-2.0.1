package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CiString20;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.BootNotificationConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.emptyOptionalException;

@RequiredArgsConstructor
@Component
public class SendBootNotificationAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createBootNotificationRequestFor(chargePointEmulator))
            .map((ThrowingFunction<BootNotificationRequest, BootNotificationConfirmation>)
                bootNotificationRequest -> callsSender.sendRequest(
                    chargePointEmulator.getCentralSystemClient(), bootNotificationRequest
                )
            )
            .map(BootNotificationConfirmation::getInterval)
            .map(Duration::ofSeconds)
            .ifPresentOrElse(
                chargePointEmulator::setHeartbeatInterval,
                () -> {throw emptyOptionalException();}
            );
    }

    private BootNotificationRequest createBootNotificationRequestFor(ChargePointEmulator chargePointEmulator) {
        return new BootNotificationRequest(
            new CiString20(chargePointEmulator.getChargePointModel()),
            new CiString20(chargePointEmulator.getChargePointVendor())
        );
    }
}
