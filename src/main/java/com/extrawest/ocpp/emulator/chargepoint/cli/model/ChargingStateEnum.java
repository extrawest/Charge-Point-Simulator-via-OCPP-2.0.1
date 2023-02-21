package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Transaction. State. Transaction_ State_ Code
 * urn:x-oca:ocpp:uid:1:569419
 * Current charging state, is required when state
 * has changed.
 *
 *
 */
public enum ChargingStateEnum {

    CHARGING("Charging"),
    EV_CONNECTED("EVConnected"),
    SUSPENDED_EV("SuspendedEV"),
    SUSPENDED_EVSE("SuspendedEVSE"),
    IDLE("Idle");
    private final String value;

    ChargingStateEnum(String value) {
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
    public static ChargingStateEnum fromValue(String value) {
        return findByField(
                ChargingStateEnum.class,
                ChargingStateEnum::value,
                value
        );
    }

}
