package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.AuthorizeConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.AuthorizeRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CiString20;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

import static java.lang.Math.min;

@RequiredArgsConstructor
@Component
public class SendAuthorizeAction implements Consumer<ChargePointEmulator> {

    private final CallsSender callsSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createAuthorizeRequestFor(chargePointEmulator))
            .map((ThrowingFunction<AuthorizeRequest, CallResult<AuthorizeConfirmation>>)
                request -> callsSender.sendCall(chargePointEmulator.getCentralSystemClient(), request)
            )
            .map(CallResult::getPayload)
            .map(AuthorizeConfirmation::getIdTagInfo)
            .ifPresentOrElse(
                chargePointEmulator::setAuthorizeIdTagInfo,
                () -> {throw ThrowReadablyUtil.emptyOptionalException();}
            );
    }

    private AuthorizeRequest createAuthorizeRequestFor(ChargePointEmulator chargePointEmulator) {
        return Optional.of(chargePointEmulator.getChargePointId())
            .map(string -> string.substring(0, min(string.length(), CiString20.VALUE_MAX_LENGTH)))
            .map(CiString20::new)
            .map(IdToken::new)
            .map(AuthorizeRequest::new)
            .get();
    }
}
