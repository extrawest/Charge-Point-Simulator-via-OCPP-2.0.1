package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class IdToken {

    @JsonValue
    private final CiString20 idToken;

    @JsonCreator
    public IdToken(@JsonProperty("idToken") CiString20 idToken) {
        this.idToken = idToken;
    }
}
