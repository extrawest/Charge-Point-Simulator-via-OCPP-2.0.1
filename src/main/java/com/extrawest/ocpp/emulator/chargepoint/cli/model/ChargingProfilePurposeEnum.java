package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Charging_ Profile. Charging_ Profile_ Purpose. Charging_ Profile_ Purpose_ Code
 * urn:x-oca:ocpp:uid:1:569231
 * Defines the purpose of the schedule transferred by this profile
 *
 *
 */
public enum ChargingProfilePurposeEnum {

    CHARGING_STATION_EXTERNAL_CONSTRAINTS("ChargingStationExternalConstraints"),
    CHARGING_STATION_MAX_PROFILE("ChargingStationMaxProfile"),
    TX_DEFAULT_PROFILE("TxDefaultProfile"),
    TX_PROFILE("TxProfile");
    private final String value;

    ChargingProfilePurposeEnum(String value) {
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
    public static ChargingProfilePurposeEnum fromValue(String value) {
        return findByField(
                ChargingProfilePurposeEnum.class,
                ChargingProfilePurposeEnum::value,
                value
        );
    }

}
