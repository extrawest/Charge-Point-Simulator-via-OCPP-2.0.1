package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.OCSPRequestData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "idToken",
        "certificate",
        "iso15118CertificateHashData"
})
@Builder
@Getter
public class AuthorizeRequest {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
     *
     * (Required)
     *
     */
    @JsonProperty("idToken")
    public IdToken idToken;
    /**
     * The X.509 certificated presented by EV and encoded in PEM format.
     *
     *
     */
    @JsonProperty("certificate")
    public String certificate;
    @JsonProperty("iso15118CertificateHashData")
    public List<OCSPRequestData> iso15118CertificateHashData;

}
