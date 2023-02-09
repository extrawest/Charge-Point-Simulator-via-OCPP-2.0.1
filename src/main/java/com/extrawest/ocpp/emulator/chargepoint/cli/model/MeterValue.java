package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Meter_ Value
 * urn:x-oca:ocpp:uid:2:233265
 * Collection of one or more sampled values in MeterValuesRequest and TransactionEvent. All sampled values in a MeterValue are sampled at the same point in time.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "sampledValue",
        "timestamp"
})
@Builder
@Getter
@EqualsAndHashCode
public class MeterValue {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("sampledValue")
    public List<SampledValue> sampledValue;
    /**
     * Meter_ Value. Timestamp. Date_ Time
     * urn:x-oca:ocpp:uid:1:569259
     * Timestamp for measured value(s).
     *
     * (Required)
     *
     */
    @JsonProperty("timestamp")
    public LocalDateTime timestamp;

}
