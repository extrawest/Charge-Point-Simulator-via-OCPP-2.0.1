package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class BootNotificationResponse {

    @JsonProperty("currentTime")
    private final LocalDateTime currentTime;

    @JsonProperty("interval")
    private final int interval;

    @JsonProperty("status")
    private final RegistrationStatus status;
}
