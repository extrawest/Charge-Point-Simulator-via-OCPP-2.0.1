package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@Getter
public class HeartbeatConfirmation {

    private final LocalDateTime currentTime;

    @JsonCreator
    public HeartbeatConfirmation(
        @JsonProperty("currentTime") @JsonFormat(pattern = DATE_FORMAT) LocalDateTime currentTime
    ) {
        this.currentTime = currentTime;
    }
}
