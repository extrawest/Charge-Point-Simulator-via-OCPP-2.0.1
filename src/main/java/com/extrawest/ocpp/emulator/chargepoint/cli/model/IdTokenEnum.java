package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Enumeration of possible idToken types.
 *
 *
 */
public enum IdTokenEnum {

    CENTRAL("Central"),
    E_MAID("eMAID"),
    ISO_14443("ISO14443"),
    ISO_15693("ISO15693"),
    KEY_CODE("KeyCode"),
    LOCAL("Local"),
    MAC_ADDRESS("MacAddress"),
    NO_AUTHORIZATION("NoAuthorization");
    private final String value;

    IdTokenEnum(String value) {
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
    public static IdTokenEnum fromValue(String value) {
        return findByField(
                IdTokenEnum.class,
                IdTokenEnum::value,
                value
        );
    }

}
