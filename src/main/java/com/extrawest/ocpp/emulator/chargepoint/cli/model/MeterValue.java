package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.IllegalArgumentApplicationException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.time.LocalDateTime;
import java.util.List;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@Builder
@Getter
public class MeterValue {

    @JsonFormat(pattern = DATE_FORMAT)
    private final LocalDateTime timestamp;

    @Singular
    @JsonProperty("sampledValue")
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
