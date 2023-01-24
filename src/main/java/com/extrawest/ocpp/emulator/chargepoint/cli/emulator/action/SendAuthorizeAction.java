package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
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
            .map(AuthorizeResponse::getIdTagInfo)
            .ifPresentOrElse(
                chargePointEmulator::setAuthorizeIdTagInfo,
                () -> {throw ThrowReadablyUtil.emptyOptionalException();}
            );
    }

    private AuthorizeRequest createAuthorizeRequestFor(ChargePointEmulator chargePointEmulator) {
        return new AuthorizeRequest(IdToken.builder().idToken(new UUID(1L, 10L).toString()).build());
        /*return Optional.of(chargePointEmulator.getChargePointId())
            .map(string -> string.substring(0, min(string.length(), CiString20.VALUE_MAX_LENGTH)))
            .map(CiString20::new)
            .map(IdToken::new)
            .map(AuthorizeRequest::new)
            .get();*/
    }

    private void notifyAuthorizeSent() {
        emulationEventsListener.onAuthorizeSent();
    }
}
