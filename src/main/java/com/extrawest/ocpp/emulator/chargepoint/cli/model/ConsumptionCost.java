package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Consumption_ Cost
 * urn:x-oca:ocpp:uid:2:233259
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "startValue",
        "cost"
})
@Builder
@Getter
public class ConsumptionCost {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Consumption_ Cost. Start_ Value. Numeric
     * urn:x-oca:ocpp:uid:1:569246
     * The lowest level of consumption that defines the starting point of this consumption block. The block interval extends to the start of the next interval.
     *
     * (Required)
     *
     */
    @JsonProperty("startValue")
    private Double startValue;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("cost")
    private List<Cost> cost = null;

}
