package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.Transaction;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.TransactionEventEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.TriggerReasonEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.TransactionEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Component
@RequiredArgsConstructor
public class SendStopTransactionAction implements Consumer<ChargePointEmulator> {

    private final RequestSender requestSender;

    private final AtomicInteger seq = new AtomicInteger();

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

    private TransactionEventRequest createRequestFor(ChargePointEmulator chargePointEmulator) {
        return TransactionEventRequest.builder()
                .eventType(TransactionEventEnum.ENDED)
                .timestamp(LocalDateTime.now())
                .triggerReason(TriggerReasonEnum.CHARGING_STATE_CHANGED)
                .seqNo(seq.incrementAndGet())
                .transactionInfo(Transaction.builder()
                        .transactionId(chargePointEmulator.getCurrentTransactionId().toString())
                        .build())
                .build();
    }
}
