package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
public class StopTransactionRequest {

    @JsonProperty("meterStop")
    private final int meterStop;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("transactionId")
    private final int transactionId;
}
