package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Used algorithms for the hashes provided.
 *
 *
 */
public enum HashAlgorithmEnum {

    SHA_256("SHA256"),
    SHA_384("SHA384"),
    SHA_512("SHA512");
    private final String value;

    HashAlgorithmEnum(String value) {
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
    public static HashAlgorithmEnum fromValue(String value) {
        return findByField(
                HashAlgorithmEnum.class,
                HashAlgorithmEnum::value,
                value
        );
    }

}
