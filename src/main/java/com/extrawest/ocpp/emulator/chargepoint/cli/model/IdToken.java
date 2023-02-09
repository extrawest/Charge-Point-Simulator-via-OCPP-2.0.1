package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.additionalInfo == null)? 0 :this.additionalInfo.hashCode()));
        result = ((result* 31)+((this.idToken == null)? 0 :this.idToken.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IdToken rhs)) {
            return false;
        }
        return Objects.equals(this.additionalInfo, rhs.additionalInfo) && Objects.equals(this.idToken, rhs.idToken) && Objects.equals(this.customData, rhs.customData) && this.type == rhs.type;
    }

}
