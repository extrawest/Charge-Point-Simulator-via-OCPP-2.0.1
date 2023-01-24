package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@ToString
public class IdTagInfo {

    @JsonProperty("expiryDate")
    private final LocalDateTime expiryDate;

    @JsonProperty("parentIdTag")
    private final String parentIdTag;

    @JsonProperty("status")
    @NonNull
    private final AuthorizationStatus status;
}
