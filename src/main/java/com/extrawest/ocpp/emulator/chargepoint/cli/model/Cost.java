package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Cost
 * urn:x-oca:ocpp:uid:2:233258
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "costKind",
        "amount",
        "amountMultiplier"
})
@Builder
@Getter
public class Cost {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Cost. Cost_ Kind. Cost_ Kind_ Code
     * urn:x-oca:ocpp:uid:1:569243
     * The kind of cost referred to in the message element amount
     *
     * (Required)
     *
     */
    @JsonProperty("costKind")
    private CostKindEnum costKind;
    /**
     * Cost. Amount. Amount
     * urn:x-oca:ocpp:uid:1:569244
     * The estimated or actual cost per kWh
     *
     * (Required)
     *
     */
    @JsonProperty("amount")
    private Integer amount;
    /**
     * Cost. Amount_ Multiplier. Integer
     * urn:x-oca:ocpp:uid:1:569245
     * Values: -3..3, The amountMultiplier defines the exponent to base 10 (dec). The final value is determined by: amount * 10 ^ amountMultiplier
     *
     *
     */
    @JsonProperty("amountMultiplier")
    private Integer amountMultiplier;

}
