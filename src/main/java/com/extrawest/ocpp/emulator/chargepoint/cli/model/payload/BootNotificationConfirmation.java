package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.RegistrationStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@Getter
public class BootNotificationConfirmation {

    private final LocalDateTime currentTime;

    private final int interval;

    private final RegistrationStatus status;

    @JsonCreator
    public BootNotificationConfirmation(
        @JsonProperty("currentTime") @JsonFormat(pattern = DATE_FORMAT) LocalDateTime currentTime,
        @JsonProperty("interval") int interval,
        @JsonProperty("status") RegistrationStatus status
    ) {
        this.currentTime = currentTime;
        this.interval = interval;
        this.status = status;
    }
}
