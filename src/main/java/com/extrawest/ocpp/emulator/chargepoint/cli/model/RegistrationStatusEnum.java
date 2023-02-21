package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * This contains whether the Charging Station has been registered
 * within the CSMS.
 *
 *
 */
public enum RegistrationStatusEnum {

    ACCEPTED("Accepted"),
    PENDING("Pending"),
    REJECTED("Rejected");
    private final String value;

    RegistrationStatusEnum(String value) {
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
    public static RegistrationStatusEnum fromValue(String value) {
        return findByField(
                RegistrationStatusEnum.class,
                RegistrationStatusEnum::value,
                value
        );
    }

}
