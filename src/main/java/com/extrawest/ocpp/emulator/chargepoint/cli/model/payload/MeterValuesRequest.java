package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalArgumentApplicationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.MeterValue;
import lombok.Builder;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Builder
public class MeterValuesRequest {

    private final int connectorId;

    private final Integer transactionId;

    @Singular
    private final List<MeterValue> meterValues;

    public MeterValuesRequest(int connectorId, int transactionId, List<MeterValue> meterValues) {
        if (connectorId < 0) {
            throw new IllegalArgumentApplicationException("connectorId must be greater than or equal to 0");
        }
        if (meterValues == null || meterValues.isEmpty()) {
            throw new IllegalArgumentApplicationException("Meter values must contain at leas one item");
        }
        this.connectorId = connectorId;
        this.transactionId = transactionId;
        this.meterValues = new ArrayList<>(meterValues);
    }
}
