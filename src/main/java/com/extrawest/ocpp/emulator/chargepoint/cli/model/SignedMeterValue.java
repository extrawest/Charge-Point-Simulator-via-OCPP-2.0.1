package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


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
@EqualsAndHashCode
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

}
