package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalStateApplicationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationIOException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.Transaction;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.TransactionEventEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.TriggerReasonEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.TransactionEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@RequiredArgsConstructor
@Component
public class SendStartTransactionAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final EmulationEventsListener emulationEventsListener;

    private final AtomicInteger seq = new AtomicInteger();

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        chargePointEmulator.setCurrentTransactionId(UUID.randomUUID());
        Optional.of(createStartTransactionRequestFor(chargePointEmulator))
            .ifPresent(request -> {
                try {
                    callsSender.sendRequest(chargePointEmulator.getCentralSystemClient(), request);
                } catch (EmulationIOException e) {
                    throw unchecked(e);
                }
                notifyStartTransactionSent();
            }
            );
    }

    private void notifyStartTransactionSent() {
        emulationEventsListener.onStartTransactionSent();
    }

    private TransactionEventRequest createStartTransactionRequestFor(ChargePointEmulator chargePointEmulator) {
        var idTag = chargePointEmulator.getIdTokenInfo();
        if (idTag == null) {
            throw new IllegalStateApplicationException("""
                The charge point does not have idTag set. \
                Please, consider sending an AuthorizeRequest first \
                and populate the emulator with the tag from the response."""
            );
        }
        return TransactionEventRequest.builder()
                .eventType(TransactionEventEnum.STARTED)
                .timestamp(LocalDateTime.now())
                .triggerReason(TriggerReasonEnum.EV_DETECTED)
                .seqNo(seq.incrementAndGet())
                .transactionInfo(Transaction.builder()
                        .transactionId(chargePointEmulator.getCurrentTransactionId().toString())
                        .build())
                .build();
    }
}
