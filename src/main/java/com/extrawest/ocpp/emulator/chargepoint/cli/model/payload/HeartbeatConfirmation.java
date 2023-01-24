package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class HeartbeatConfirmation {

    @JsonProperty("currentTime")
    private final LocalDateTime currentTime;
}
