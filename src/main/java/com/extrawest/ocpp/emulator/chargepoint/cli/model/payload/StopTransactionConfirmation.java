package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTagInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
@ToString
public class StopTransactionConfirmation {

    @JsonProperty("idTagInfo")
    private final IdTagInfo idTagInfo;
}
