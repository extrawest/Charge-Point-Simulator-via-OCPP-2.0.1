package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * This contains the reason for sending this message to the CSMS.
 *
 *
 */
public enum BootReasonEnum {

    APPLICATION_RESET("ApplicationReset"),
    FIRMWARE_UPDATE("FirmwareUpdate"),
    LOCAL_RESET("LocalReset"),
    POWER_UP("PowerUp"),
    REMOTE_RESET("RemoteReset"),
    SCHEDULED_RESET("ScheduledReset"),
    TRIGGERED("Triggered"),
    UNKNOWN("Unknown"),
    WATCHDOG("Watchdog");
    private final String value;

    BootReasonEnum(String value) {
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
    public static BootReasonEnum fromValue(String value) {
        return findByField(
                BootReasonEnum.class,
                BootReasonEnum::value,
                value
        );
    }

}
