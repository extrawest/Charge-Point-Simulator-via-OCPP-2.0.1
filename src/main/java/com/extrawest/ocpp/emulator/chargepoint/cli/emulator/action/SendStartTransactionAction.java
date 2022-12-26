package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.IdTokenGenerator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.StartTransactionConfirmation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.StartTransactionRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.emptyOptionalException;
import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@RequiredArgsConstructor
@Component
public class SendStartTransactionAction implements Consumer<ChargePointEmulator> {

    private static final int DEFAULT_CONNECTOR_ID = 1;

    private final CallsSender callsSender;

    private final IdTokenGenerator idTokenGenerator;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createStartTransactionRequestFor(chargePointEmulator))
            .map((ThrowingFunction<StartTransactionRequest, CallResult<StartTransactionConfirmation>>)
                request -> callsSender.sendCall(chargePointEmulator.getCentralSystemClient(), request)
            )
            .map(CallResult::getPayload)
            .map(StartTransactionConfirmation::getTransactionId)
            .ifPresentOrElse(
                chargePointEmulator::setCurrentTransactionId,
                () -> {throw emptyOptionalException();}
            );
    }

    private StartTransactionRequest createStartTransactionRequestFor(ChargePointEmulator chargePointEmulator) {
        var idTag = chargePointEmulator.getAuthorizeIdTagInfo();
        if (idTag == null) {
            throw unchecked(new IllegalStateException("""
                The charge point does not have idTag set. \
                Please, consider sending an AuthorizeRequest first \
                and populate the emulator with the tag from the response."""
            ));
        }
        return new StartTransactionRequest(
            DEFAULT_CONNECTOR_ID,
            idTokenGenerator.generateRandomToken(),
            chargePointEmulator.getCurrentMeterValue(),
            LocalDateTime.now()
        );
    }
}
