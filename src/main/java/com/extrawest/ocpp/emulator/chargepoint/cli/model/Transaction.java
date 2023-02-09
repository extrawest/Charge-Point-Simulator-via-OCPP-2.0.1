package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;


/**
 * Transaction
 * urn:x-oca:ocpp:uid:2:233318
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "transactionId",
        "chargingState",
        "timeSpentCharging",
        "stoppedReason",
        "remoteStartId"
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class Transaction {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * This contains the Id of the transaction.
     *
     * (Required)
     *
     */
    @JsonProperty("transactionId")
    public String transactionId;
    /**
     * Transaction. State. Transaction_ State_ Code
     * urn:x-oca:ocpp:uid:1:569419
     * Current charging state, is required when state
     * has changed.
     *
     *
     */
    @JsonProperty("chargingState")
    public ChargingStateEnum chargingState;
    /**
     * Transaction. Time_ Spent_ Charging. Elapsed_ Time
     * urn:x-oca:ocpp:uid:1:569415
     * Contains the total time that energy flowed from EVSE to EV during the transaction (in seconds). Note that timeSpentCharging is smaller or equal to the duration of the transaction.
     *
     *
     */
    @JsonProperty("timeSpentCharging")
    public Integer timeSpentCharging;
    /**
     * Transaction. Stopped_ Reason. EOT_ Reason_ Code
     * urn:x-oca:ocpp:uid:1:569413
     * This contains the reason why the transaction was stopped. MAY only be omitted when Reason is "Local".
     *
     *
     */
    @JsonProperty("stoppedReason")
    public ReasonEnum stoppedReason;
    /**
     * The ID given to remote start request (&lt;<requeststarttransactionrequest, RequestStartTransactionRequest>>. This enables to CSMS to match the started transaction to the given start request.
     *
     *
     */
    @JsonProperty("remoteStartId")
    public Integer remoteStartId;

}
