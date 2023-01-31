package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "hashAlgorithm",
        "issuerNameHash",
        "issuerKeyHash",
        "serialNumber",
        "responderURL"
})
public class OCSPRequestData {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Used algorithms for the hashes provided.
     *
     * (Required)
     *
     */
    @JsonProperty("hashAlgorithm")
    public HashAlgorithmEnum hashAlgorithm;
    /**
     * Hashed value of the Issuer DN (Distinguished Name).
     *
     *
     * (Required)
     *
     */
    @JsonProperty("issuerNameHash")
    public String issuerNameHash;
    /**
     * Hashed value of the issuers public key
     *
     * (Required)
     *
     */
    @JsonProperty("issuerKeyHash")
    public String issuerKeyHash;
    /**
     * The serial number of the certificate.
     *
     * (Required)
     *
     */
    @JsonProperty("serialNumber")
    public String serialNumber;
    /**
     * This contains the responder URL (Case insensitive).
     *
     *
     * (Required)
     *
     */
    @JsonProperty("responderURL")
    public String responderURL;

}
