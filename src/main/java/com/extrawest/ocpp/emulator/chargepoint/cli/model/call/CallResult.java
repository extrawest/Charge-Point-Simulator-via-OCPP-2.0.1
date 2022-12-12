package com.extrawest.ocpp.emulator.chargepoint.cli.model.call;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "uniqueId")
@ToString
public class CallResult<T> {

    private MessageType messageType;

    private String uniqueId;

    private T payload;
}
