package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.StopTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.transactionNotStarted;
import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Component
@RequiredArgsConstructor
public class SendStopTransactionAction implements Consumer<ChargePointEmulator> {

    private final RequestSender requestSender;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var request = createRequestFor(chargePointEmulator);
        var client = chargePointEmulator.getCentralSystemClient();
        try {
            requestSender.sendRequest(client, request);
            chargePointEmulator.setCurrentTransactionId(null);
        } catch (EmulationIOException e) {
            throw unchecked(e);
        }
    }

    private StopTransactionRequest createRequestFor(ChargePointEmulator chargePointEmulator) {
        return new StopTransactionRequest(
            chargePointEmulator.getCurrentMeterValue().get(),
            LocalDateTime.now(),
            Optional.of(chargePointEmulator.getCurrentTransactionId())
                .orElseThrow(() -> transactionNotStarted(chargePointEmulator))
        );
    }
}
