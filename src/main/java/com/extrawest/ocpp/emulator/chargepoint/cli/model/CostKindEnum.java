package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Cost. Cost_ Kind. Cost_ Kind_ Code
 * urn:x-oca:ocpp:uid:1:569243
 * The kind of cost referred to in the message element amount
 *
 *
 */
public enum CostKindEnum {

    CARBON_DIOXIDE_EMISSION("CarbonDioxideEmission"),
    RELATIVE_PRICE_PERCENTAGE("RelativePricePercentage"),
    RENEWABLE_GENERATION_PERCENTAGE("RenewableGenerationPercentage");
    private final String value;

    CostKindEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static CostKindEnum fromValue(String value) {
        return findByField(
                CostKindEnum.class,
                CostKindEnum::value,
                value
        );
    }

}
