package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class HeartbeatConfirmation {

    @JsonProperty("currentTime")
    @JsonFormat(pattern = DATE_FORMAT)
    private final LocalDateTime currentTime;
}
