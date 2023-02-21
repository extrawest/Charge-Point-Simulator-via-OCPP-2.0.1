package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;

public enum MessageType {

    CALL(2),
    CALL_RESULT(3),
    CALL_ERROR(4);

    private final int messageTypeId;

    MessageType(int messageTypeId) {
        this.messageTypeId = messageTypeId;
    }

    @JsonValue
    public int getMessageTypeId() {
        return messageTypeId;
    }

    public static MessageType fromMessageTypeId(int messageTypeId) {
        return Arrays.stream(MessageType.values())
            .filter(messageType -> messageType.getMessageTypeId() == messageTypeId)
            .findFirst()
            .orElseThrow(
                () -> new IllegalArgumentException("MessageType (id = %d) was not found".formatted(messageTypeId))
            );
    }
}
