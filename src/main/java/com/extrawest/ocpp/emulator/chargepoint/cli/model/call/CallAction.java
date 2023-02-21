package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;

public enum CallAction {

    AUTHORIZE("Authorize"),
    BOOT_NOTIFICATION("BootNotification"),
    HEARTBEAT("Heartbeat"),
    TRANSACTION_EVENT("TransactionEvent"),
    REQUEST_START_TRANSACTION("RequestStartTransaction"),
    REQUEST_STOP_TRANSACTION("RequestStopTransaction");

    private final String value;

    CallAction(String value) {
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
    public static CallAction fromValue(String value) {
        return findByField(
                CallAction.class,
                CallAction::value,
                value
        );
    }
}
