package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Charging_ Profile. Recurrency_ Kind. Recurrency_ Kind_ Code
 * urn:x-oca:ocpp:uid:1:569233
 * Indicates the start point of a recurrence.
 *
 *
 */
public enum RecurrencyKindEnum {

    DAILY("Daily"),
    WEEKLY("Weekly");
    private final String value;

    RecurrencyKindEnum(String value) {
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
    public static RecurrencyKindEnum fromValue(String value) {
        return findByField(
                RecurrencyKindEnum.class,
                RecurrencyKindEnum::value,
                value
        );
    }

}
