package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdTokenInfo;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.MessageContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "totalCost",
        "chargingPriority",
        "idTokenInfo",
        "updatedPersonalMessage"
})
@Getter
@EqualsAndHashCode
public class TransactionEventResponse {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * SHALL only be sent when charging has ended. Final total cost of this transaction, including taxes. In the currency configured with the Configuration Variable: &lt;<configkey-currency,`Currency`>>. When omitted, the transaction was NOT free. To indicate a free transaction, the CSMS SHALL send 0.00.
     *
     *
     *
     */
    @JsonProperty("totalCost")
    public Double totalCost;
    /**
     * Priority from a business point of view. Default priority is 0, The range is from -9 to 9. Higher values indicate a higher priority. The chargingPriority in &lt;<transactioneventresponse,TransactionEventResponse>> is temporarily, so it may not be set in the <<cmn_idtokeninfotype,IdTokenInfoType>> afterwards. Also the chargingPriority in <<transactioneventresponse,TransactionEventResponse>> overrules the one in <<cmn_idtokeninfotype,IdTokenInfoType>>.
     *
     *
     */
    @JsonProperty("chargingPriority")
    public Integer chargingPriority;
    /**
     * ID_ Token
     * urn:x-oca:ocpp:uid:2:233247
     * Contains status information about an identifier.
     * It is advised to not stop charging for a token that expires during charging, as ExpiryDate is only used for caching purposes. If ExpiryDate is not given, the status has no end date.
     *
     *
     */
    @JsonProperty("idTokenInfo")
    public IdTokenInfo idTokenInfo;
    /**
     * Message_ Content
     * urn:x-enexis:ecdm:uid:2:234490
     * Contains message details, for a message to be displayed on a Charging Station.
     *
     *
     *
     */
    @JsonProperty("updatedPersonalMessage")
    public MessageContent updatedPersonalMessage;

}
