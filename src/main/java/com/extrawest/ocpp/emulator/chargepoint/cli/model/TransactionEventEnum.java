package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * This contains the type of this event.
 * The first TransactionEvent of a transaction SHALL contain: "Started" The last TransactionEvent of a transaction SHALL contain: "Ended" All others SHALL contain: "Updated"
 *
 *
 */
public enum TransactionEventEnum {

    ENDED("Ended"),
    STARTED("Started"),
    UPDATED("Updated");
    private final String value;

    TransactionEventEnum(String value) {
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
    public static TransactionEventEnum fromValue(String value) {
        return findByField(
                TransactionEventEnum.class,
                TransactionEventEnum::value,
                value
        );
    }

}
