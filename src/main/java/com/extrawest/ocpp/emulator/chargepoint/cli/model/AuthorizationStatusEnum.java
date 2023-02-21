package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * ID_ Token. Status. Authorization_ Status
 * urn:x-oca:ocpp:uid:1:569372
 * Current status of the ID Token.
 *
 *
 */
public enum AuthorizationStatusEnum {

    ACCEPTED("Accepted"),
    BLOCKED("Blocked"),
    CONCURRENT_TX("ConcurrentTx"),
    EXPIRED("Expired"),
    INVALID("Invalid"),
    NO_CREDIT("NoCredit"),
    NOT_ALLOWED_TYPE_EVSE("NotAllowedTypeEVSE"),
    NOT_AT_THIS_LOCATION("NotAtThisLocation"),
    NOT_AT_THIS_TIME("NotAtThisTime"),
    UNKNOWN("Unknown");
    private final String value;

    AuthorizationStatusEnum(String value) {
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
    public static AuthorizationStatusEnum fromValue(String value) {
        return findByField(
                AuthorizationStatusEnum.class,
                AuthorizationStatusEnum::value,
                value
        );
    }

}
