package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.AuthorizeCertificateStatusEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTokenInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "idTokenInfo",
        "certificateStatus"
})
@Getter
public class AuthorizeResponse {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * ID_ Token
     * urn:x-oca:ocpp:uid:2:233247
     * Contains status information about an identifier.
     * It is advised to not stop charging for a token that expires during charging, as ExpiryDate is only used for caching purposes. If ExpiryDate is not given, the status has no end date.
     *
     * (Required)
     *
     */
    @JsonProperty("idTokenInfo")
    public IdTokenInfo idTokenInfo;
    /**
     * Certificate status information.
     * - if all certificates are valid: return 'Accepted'.
     * - if one of the certificates was revoked, return 'CertificateRevoked'.
     *
     *
     */
    @JsonProperty("certificateStatus")
    public AuthorizeCertificateStatusEnum certificateStatus;

}
