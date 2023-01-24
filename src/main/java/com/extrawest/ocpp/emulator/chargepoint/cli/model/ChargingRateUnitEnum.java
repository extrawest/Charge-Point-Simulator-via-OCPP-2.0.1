package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Charging_ Schedule. Charging_ Rate_ Unit. Charging_ Rate_ Unit_ Code
 * urn:x-oca:ocpp:uid:1:569238
 * The unit of measure Limit is expressed in.
 *
 *
 */
public enum ChargingRateUnitEnum {

    W("W"),
    A("A");
    private final String value;
    private final static Map<String, ChargingRateUnitEnum> CONSTANTS = new HashMap<>();

    static {
        for (ChargingRateUnitEnum c: values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    ChargingRateUnitEnum(String value) {
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
    public static ChargingRateUnitEnum fromValue(String value) {
        ChargingRateUnitEnum constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
