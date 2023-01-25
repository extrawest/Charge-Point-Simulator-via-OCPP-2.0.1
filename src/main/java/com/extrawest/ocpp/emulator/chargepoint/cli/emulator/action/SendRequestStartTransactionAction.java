package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.RequestStartTransactionRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.RequestStartTransactionResponse;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class SendRequestStartTransactionAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final EmulationEventsListener emulationEventsListener;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createRequestStartTransactionRequestFor(chargePointEmulator))
                .map((ThrowingFunction<RequestStartTransactionRequest, RequestStartTransactionResponse>)
                        requestStartTransactionRequest -> {
                            var response = callsSender.sendRequest(
                                    chargePointEmulator.getCentralSystemClient(), requestStartTransactionRequest
                            );
                    notifyRequestStartTransactionSent();
                            return response;
                });
    }

    private RequestStartTransactionRequest createRequestStartTransactionRequestFor(ChargePointEmulator chargePointEmulator) {
        return RequestStartTransactionRequest.builder()
                .remoteStartId(1)//todo
                .idToken(IdToken.builder()
                        .idToken(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    private void notifyRequestStartTransactionSent() {
        emulationEventsListener.onRequestStartTransactionSent();
    }
}
