package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * ID_ Token
 * urn:x-oca:ocpp:uid:2:233247
 * Contains status information about an identifier.
 * It is advised to not stop charging for a token that expires during charging, as ExpiryDate is only used for caching purposes. If ExpiryDate is not given, the status has no end date.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "status",
        "cacheExpiryDateTime",
        "chargingPriority",
        "language1",
        "evseId",
        "groupIdToken",
        "language2",
        "personalMessage"
})
@Builder
@Getter
public class IdTokenInfo {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * ID_ Token. Status. Authorization_ Status
     * urn:x-oca:ocpp:uid:1:569372
     * Current status of the ID Token.
     *
     * (Required)
     *
     */
    @JsonProperty("status")
    public AuthorizationStatusEnum status;
    /**
     * ID_ Token. Expiry. Date_ Time
     * urn:x-oca:ocpp:uid:1:569373
     * Date and Time after which the token must be considered invalid.
     *
     *
     */
    @JsonProperty("cacheExpiryDateTime")
    public Date cacheExpiryDateTime;
    /**
     * Priority from a business point of view. Default priority is 0, The range is from -9 to 9. Higher values indicate a higher priority. The chargingPriority in &lt;<transactioneventresponse,TransactionEventResponse>> overrules this one.
     *
     *
     */
    @JsonProperty("chargingPriority")
    public Integer chargingPriority;
    /**
     * ID_ Token. Language1. Language_ Code
     * urn:x-oca:ocpp:uid:1:569374
     * Preferred user interface language of identifier user. Contains a language code as defined in &lt;<ref-RFC5646,[RFC5646]>>.
     *
     *
     *
     */
    @JsonProperty("language1")
    public String language1;
    /**
     * Only used when the IdToken is only valid for one or more specific EVSEs, not for the entire Charging Station.
     *
     *
     *
     */
    @JsonProperty("evseId")
    public List<Integer> evseId = null;
    /**
     * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
     *
     *
     */
    @JsonProperty("groupIdToken")
    public IdToken groupIdToken;
    /**
     * ID_ Token. Language2. Language_ Code
     * urn:x-oca:ocpp:uid:1:569375
     * Second preferred user interface language of identifier user. Don’t use when language1 is omitted, has to be different from language1. Contains a language code as defined in &lt;<ref-RFC5646,[RFC5646]>>.
     *
     *
     */
    @JsonProperty("language2")
    public String language2;
    /**
     * Message_ Content
     * urn:x-enexis:ecdm:uid:2:234490
     * Contains message details, for a message to be displayed on a Charging Station.
     *
     *
     *
     */
    @JsonProperty("personalMessage")
    public MessageContent personalMessage;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.evseId == null)? 0 :this.evseId.hashCode()));
        result = ((result* 31)+((this.language2 == null)? 0 :this.language2 .hashCode()));
        result = ((result* 31)+((this.language1 == null)? 0 :this.language1 .hashCode()));
        result = ((result* 31)+((this.cacheExpiryDateTime == null)? 0 :this.cacheExpiryDateTime.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.chargingPriority == null)? 0 :this.chargingPriority.hashCode()));
        result = ((result* 31)+((this.personalMessage == null)? 0 :this.personalMessage.hashCode()));
        result = ((result* 31)+((this.groupIdToken == null)? 0 :this.groupIdToken.hashCode()));
        result = ((result* 31)+((this.status == null)? 0 :this.status.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof IdTokenInfo rhs)) {
            return false;
        }
        return (((((((((Objects.equals(this.evseId, rhs.evseId))&&(Objects.equals(this.language2, rhs.language2)))&&(Objects.equals(this.language1, rhs.language1)))&&(Objects.equals(this.cacheExpiryDateTime, rhs.cacheExpiryDateTime)))&&(Objects.equals(this.customData, rhs.customData)))&&(Objects.equals(this.chargingPriority, rhs.chargingPriority)))&&(Objects.equals(this.personalMessage, rhs.personalMessage)))&&(Objects.equals(this.groupIdToken, rhs.groupIdToken)))&&(Objects.equals(this.status, rhs.status)));
    }

}
