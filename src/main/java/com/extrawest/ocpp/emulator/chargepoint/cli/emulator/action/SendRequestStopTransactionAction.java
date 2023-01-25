package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.RequestStopTransactionRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.RequestStopTransactionResponse;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class SendRequestStopTransactionAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final EmulationEventsListener emulationEventsListener;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        Optional.of(createRequestStopTransactionRequestFor(chargePointEmulator))
                .map((ThrowingFunction<RequestStopTransactionRequest, RequestStopTransactionResponse>)
                        requestStartTransactionRequest -> {
                            var response = callsSender.sendRequest(
                                    chargePointEmulator.getCentralSystemClient(), requestStartTransactionRequest
                            );
                            notifyRequestStopTransactionSent();
                            return response;
                        });
    }

    private RequestStopTransactionRequest createRequestStopTransactionRequestFor(ChargePointEmulator chargePointEmulator) {
        return RequestStopTransactionRequest.builder()
                .transactionId(chargePointEmulator.getCurrentTransactionId().toString())
                .build();
    }

    private void notifyRequestStopTransactionSent() {
        emulationEventsListener.onRequestStopTransactionSent();
    }
}
