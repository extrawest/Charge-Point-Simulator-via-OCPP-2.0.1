package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Status indicating whether the Charging Station accepts the request to start a transaction.
 *
 *
 */
public enum RequestStartStopStatusEnum {

    ACCEPTED("Accepted"),
    REJECTED("Rejected");
    private final String value;

    RequestStartStopStatusEnum(String value) {
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
    public static RequestStartStopStatusEnum fromValue(String value) {
        return findByField(
                RequestStartStopStatusEnum.class,
                RequestStartStopStatusEnum::value,
                value
        );
    }

}
