package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.RequestSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.EmulationEventsListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.*;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.payload.TransactionEventRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.helper.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Component
public class StartSendingMeterValuesAction implements Consumer<ChargePointEmulator> {

    private final RequestSender callsSender;

    private final ScheduledExecutorService scheduledExecutorService;

    private final EmulationEventsListener emulationEventsListener;
    private final AtomicInteger seq = new AtomicInteger();

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        long meterValuesSendInterval = chargePointEmulator.getSendMeterValuesInterval().toMillis();
        scheduledExecutorService.scheduleAtFixedRate(
            (ThrowingRunnable) () -> {
                callsSender.sendRequest(
                    chargePointEmulator.getCentralSystemClient(),
                    createTransactionEventRequest(chargePointEmulator)
                );
                notifyMeterValuesSent();
            },
            meterValuesSendInterval,
            meterValuesSendInterval,
            TimeUnit.MILLISECONDS
        );
    }

    private TransactionEventRequest createTransactionEventRequest(ChargePointEmulator chargePointEmulator) {
        return TransactionEventRequest.builder()
                .eventType(TransactionEventEnum.UPDATED)
                .timestamp(LocalDateTime.now())
                .triggerReason(TriggerReasonEnum.METER_VALUE_PERIODIC)
                .seqNo(seq.incrementAndGet())
                .transactionInfo(Transaction.builder()
                        .transactionId(chargePointEmulator.getCurrentTransactionId().toString())
                        .build())
                .build();
    }

    private void notifyMeterValuesSent() {
        emulationEventsListener.onMeterValuesSent();
    }
}
