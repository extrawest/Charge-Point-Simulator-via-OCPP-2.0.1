package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "uniqueId")
@ToString
public class Call<T> {

    private final MessageType messageTypeId = MessageType.CALL;

    private final String uniqueId; // TODO: rethink if a class-wrapper is required

    private final CallAction action;

    private final T payload;
}
