package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;


/**
 * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "additionalInfo",
        "idToken",
        "type"
})
@Builder
@Getter
@EqualsAndHashCode
public class IdToken {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    @JsonProperty("additionalInfo")
    public List<AdditionalInfo> additionalInfo = null;
    /**
     * IdToken is case insensitive. Might hold the hidden id of an RFID tag, but can for example also contain a UUID.
     *
     * (Required)
     *
     */
    @JsonProperty("idToken")
    public String idToken;
    /**
     * Enumeration of possible idToken types.
     *
     * (Required)
     *
     */
    @JsonProperty("type")
    public IdTokenEnum type;

}
