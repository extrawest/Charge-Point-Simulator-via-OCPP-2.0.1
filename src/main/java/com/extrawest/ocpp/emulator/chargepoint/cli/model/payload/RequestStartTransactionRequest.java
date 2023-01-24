package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.ChargingProfile;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "evseId",
        "groupIdToken",
        "idToken",
        "remoteStartId",
        "chargingProfile"
})
@Builder
@Getter
public class RequestStartTransactionRequest {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Number of the EVSE on which to start the transaction. EvseId SHALL be &gt; 0
     *
     *
     */
    @JsonProperty("evseId")
    private Integer evseId;
    /**
     * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
     *
     *
     */
    @JsonProperty("groupIdToken")
    private IdToken groupIdToken;
    /**
     * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
     *
     * (Required)
     *
     */
    @JsonProperty("idToken")
    private IdToken idToken;
    /**
     * Id given by the server to this start request. The Charging Station might return this in the &lt;<transactioneventrequest, TransactionEventRequest>>, letting the server know which transaction was started for this request. Use to start a transaction.
     *
     * (Required)
     *
     */
    @JsonProperty("remoteStartId")
    private Integer remoteStartId;
    /**
     * Charging_ Profile
     * urn:x-oca:ocpp:uid:2:233255
     * A ChargingProfile consists of ChargingSchedule, describing the amount of power or current that can be delivered per time interval.
     *
     *
     */
    @JsonProperty("chargingProfile")
    private ChargingProfile chargingProfile;

}
