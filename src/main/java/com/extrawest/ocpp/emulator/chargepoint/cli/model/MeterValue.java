package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalArgumentApplicationException;
import lombok.Builder;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class MeterValue {

    private final LocalDateTime timestamp;

    @Singular
    private final List<SampledValue> sampledValues;

    public MeterValue(LocalDateTime timestamp, List<SampledValue> sampledValues) {
        ensureParametersValidOrThrow(timestamp, sampledValues);
        this.timestamp = timestamp;
        this.sampledValues = sampledValues;
    }

    private void ensureParametersValidOrThrow(LocalDateTime timestamp, List<SampledValue> sampledValues) {
        if (timestamp == null) {
            throw new IllegalArgumentApplicationException("Timestamp must be defined");
        }
        if (sampledValues == null || sampledValues.isEmpty()) {
            throw new IllegalArgumentApplicationException("Sampled values must contain at least one item");
        }
    }
}
