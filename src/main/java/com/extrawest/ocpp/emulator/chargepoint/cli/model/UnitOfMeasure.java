package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * Represents a UnitOfMeasure with a multiplier
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "unit",
        "multiplier"
})
@Builder
@Getter
@EqualsAndHashCode
public class UnitOfMeasure {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Unit of the value. Default = "Wh" if the (default) measurand is an "Energy" type.
     * This field SHALL use a value from the list Standardized Units of Measurements in Part 2 Appendices.
     * If an applicable unit is available in that list, otherwise a "custom" unit might be used.
     *
     *
     */
    @JsonProperty("unit")
    public String unit = "Wh";
    /**
     * Multiplier, this value represents the exponent to base 10. I.e. multiplier 3 means 10 raised to the 3rd power. Default is 0.
     *
     *
     */
    @JsonProperty("multiplier")
    public Integer multiplier = 0;

}
