package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;


/**
 * Represent a signed version of the meter value.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "signedMeterData",
        "signingMethod",
        "encodingMethod",
        "publicKey"
})
@Builder
@Getter
public class SignedMeterValue {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Base64 encoded, contains the signed data which might contain more then just the meter value. It can contain information like timestamps, reference to a customer etc.
     *
     * (Required)
     *
     */
    @JsonProperty("signedMeterData")
    public String signedMeterData;
    /**
     * Method used to create the digital signature.
     *
     * (Required)
     *
     */
    @JsonProperty("signingMethod")
    public String signingMethod;
    /**
     * Method used to encode the meter values before applying the digital signature algorithm.
     *
     * (Required)
     *
     */
    @JsonProperty("encodingMethod")
    public String encodingMethod;
    /**
     * Base64 encoded, sending depends on configuration variable _PublicKeyWithSignedMeterValue_.
     *
     * (Required)
     *
     */
    @JsonProperty("publicKey")
    public String publicKey;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.signedMeterData == null)? 0 :this.signedMeterData.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.publicKey == null)? 0 :this.publicKey.hashCode()));
        result = ((result* 31)+((this.signingMethod == null)? 0 :this.signingMethod.hashCode()));
        result = ((result* 31)+((this.encodingMethod == null)? 0 :this.encodingMethod.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SignedMeterValue rhs)) {
            return false;
        }
        return (((((Objects.equals(this.signedMeterData, rhs.signedMeterData))&&(Objects.equals(this.customData, rhs.customData)))&&(Objects.equals(this.publicKey, rhs.publicKey)))&&(Objects.equals(this.signingMethod, rhs.signingMethod)))&&(Objects.equals(this.encodingMethod, rhs.encodingMethod)));
    }
}
