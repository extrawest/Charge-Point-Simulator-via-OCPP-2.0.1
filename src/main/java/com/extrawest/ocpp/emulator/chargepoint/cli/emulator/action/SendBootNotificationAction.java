package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootReasonEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.ChargingStation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.Modem;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.BootNotificationRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.BootNotificationResponse;
import com.extrawest.ocpp.emulator.chargepoint.cli.helper.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.emptyOptionalException;

@RequiredArgsConstructor
@Component
public class SendBootNotificationAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final EmulationEventsListener emulationEventsListener;
    @Value("${ocpp.charge-point.boot-notification.chargePointModel}")
    private String model;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createBootNotificationRequestFor(chargePointEmulator))
            .map((ThrowingFunction<BootNotificationRequest, BootNotificationResponse>)
                bootNotificationRequest -> {
                    var response = callsSender.sendRequest(
                        chargePointEmulator.getCentralSystemClient(), bootNotificationRequest
                    );
                    notifyBootNotificationSent();
                    return response;
                }
            )
            .map(BootNotificationResponse::getInterval)
            .map(Duration::ofSeconds)
            .ifPresentOrElse(
                chargePointEmulator::setHeartbeatInterval,
                () -> {throw emptyOptionalException();}
            );
    }

    private void notifyBootNotificationSent() {
        emulationEventsListener.onBootNotificationSent();
    }

    private BootNotificationRequest createBootNotificationRequestFor(ChargePointEmulator chargePointEmulator) {
        return BootNotificationRequest.builder()
                .chargingStation(
                        ChargingStation.builder()
                                .vendorName(chargePointEmulator.getChargePointVendor())
                                .modem(Modem.builder().build())
                                .model(model)
                                .build()
                )
                .reason(BootReasonEnum.POWER_UP)
                .build();
    }
}
