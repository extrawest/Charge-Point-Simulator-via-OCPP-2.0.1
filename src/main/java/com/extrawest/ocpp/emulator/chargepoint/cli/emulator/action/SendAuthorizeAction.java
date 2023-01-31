package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTokenEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.AuthorizeResponse;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.AuthorizeRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static java.lang.Math.min;

@RequiredArgsConstructor
@Component
public class SendAuthorizeAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final EmulationEventsListener emulationEventsListener;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createAuthorizeRequestFor(chargePointEmulator))
            .map((ThrowingFunction<AuthorizeRequest, AuthorizeResponse>)
                request -> {
                    var response = callsSender.sendRequest(chargePointEmulator.getCentralSystemClient(), request);
                    notifyAuthorizeSent();
                    return response;
                }
            )
            .map(AuthorizeResponse::getIdTokenInfo)
            .ifPresentOrElse(
                chargePointEmulator::setIdTokenInfo,
                () -> {throw ThrowReadablyUtil.emptyOptionalException();}
            );
    }

    private AuthorizeRequest createAuthorizeRequestFor(ChargePointEmulator chargePointEmulator) {
        return AuthorizeRequest.builder()
                .idToken(IdToken.builder()
                        .idToken(UUID.randomUUID().toString())
                        .type(IdTokenEnum.MAC_ADDRESS)
                        .build())
                .build();
    }

    private void notifyAuthorizeSent() {
        emulationEventsListener.onAuthorizeSent();
    }
}
