package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.CallsSender;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.MeterValue;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.MeterValuesRequest;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.SampledValue;
import com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowingRunnable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DEFAULT_CONNECTOR_ID;

@RequiredArgsConstructor
@Component
public class StartSendingMeterValuesAction implements Consumer<ChargePointEmulator> {

    private final CallsSender callsSender;

    private final ScheduledExecutorService scheduledExecutorService;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        long meterValuesSendInterval = chargePointEmulator.getSendMeterValuesInterval().toMillis();
        scheduledExecutorService.scheduleWithFixedDelay(
            (ThrowingRunnable) () -> callsSender.sendCall(
                chargePointEmulator.getCentralSystemClient(),
                incrementMeterValuesAndCreateRequest(chargePointEmulator)
            ),
            meterValuesSendInterval,
            meterValuesSendInterval,
            TimeUnit.MILLISECONDS
        );
    }

    private MeterValuesRequest incrementMeterValuesAndCreateRequest(ChargePointEmulator chargePointEmulator) {
        int meterValueMaxIncrement = 100;
        int incrementedMeterValue = (int)
            (chargePointEmulator.getCurrentMeterValue() + Math.random() * meterValueMaxIncrement);
        chargePointEmulator.setCurrentMeterValue(incrementedMeterValue);
        return MeterValuesRequest.builder()
            .connectorId(DEFAULT_CONNECTOR_ID)
            .transactionId(chargePointEmulator.getCurrentTransactionId())
            .meterValue(
                MeterValue.builder()
                    .timestamp(LocalDateTime.now())
                    .sampledValue(new SampledValue(String.valueOf(incrementedMeterValue)))
                    .build()
            )
            .build();
    }
}
