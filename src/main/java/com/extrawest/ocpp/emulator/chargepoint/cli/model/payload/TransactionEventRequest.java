package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "eventType",
        "meterValue",
        "timestamp",
        "triggerReason",
        "seqNo",
        "offline",
        "numberOfPhasesUsed",
        "cableMaxCurrent",
        "reservationId",
        "transactionInfo",
        "evse",
        "idToken"
})
@Getter
@Builder
@ToString
public class TransactionEventRequest {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * This contains the type of this event.
     * The first TransactionEvent of a transaction SHALL contain: "Started" The last TransactionEvent of a transaction SHALL contain: "Ended" All others SHALL contain: "Updated"
     *
     * (Required)
     *
     */
    @JsonProperty("eventType")
    public TransactionEventEnum eventType;
    @JsonProperty("meterValue")
    public List<MeterValue> meterValue = null;
    /**
     * The date and time at which this transaction event occurred.
     *
     * (Required)
     *
     */
    @JsonProperty("timestamp")
    @JsonFormat(pattern = DATE_FORMAT)
    public LocalDateTime timestamp;
    /**
     * Reason the Charging Station sends this message to the CSMS
     *
     * (Required)
     *
     */
    @JsonProperty("triggerReason")
    public TriggerReasonEnum triggerReason;
    /**
     * Incremental sequence number, helps with determining if all messages of a transaction have been received.
     *
     * (Required)
     *
     */
    @JsonProperty("seqNo")
    public Integer seqNo;
    /**
     * Indication that this transaction event happened when the Charging Station was offline. Default = false, meaning: the event occurred when the Charging Station was online.
     *
     *
     */
    @JsonProperty("offline")
    public Boolean offline = false;
    /**
     * If the Charging Station is able to report the number of phases used, then it SHALL provide it. When omitted the CSMS may be able to determine the number of phases used via device management.
     *
     *
     */
    @JsonProperty("numberOfPhasesUsed")
    public Integer numberOfPhasesUsed;
    /**
     * The maximum current of the connected cable in Ampere (A).
     *
     *
     */
    @JsonProperty("cableMaxCurrent")
    public Integer cableMaxCurrent;
    /**
     * This contains the Id of the reservation that terminates as a result of this transaction.
     *
     *
     */
    @JsonProperty("reservationId")
    public Integer reservationId;
    /**
     * Transaction
     * urn:x-oca:ocpp:uid:2:233318
     *
     * (Required)
     *
     */
    @JsonProperty("transactionInfo")
    public Transaction transactionInfo;
    /**
     * EVSE
     * urn:x-oca:ocpp:uid:2:233123
     * Electric Vehicle Supply Equipment
     *
     *
     */
    @JsonProperty("evse")
    public EVSE evse;
    /**
     * Contains a case insensitive identifier to use for the authorization and the type of authorization to support multiple forms of identifiers.
     *
     *
     */
    @JsonProperty("idToken")
    public IdToken idToken;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.seqNo == null)? 0 :this.seqNo.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.eventType == null)? 0 :this.eventType.hashCode()));
        result = ((result* 31)+((this.evse == null)? 0 :this.evse.hashCode()));
        result = ((result* 31)+((this.transactionInfo == null)? 0 :this.transactionInfo.hashCode()));
        result = ((result* 31)+((this.offline == null)? 0 :this.offline.hashCode()));
        result = ((result* 31)+((this.reservationId == null)? 0 :this.reservationId.hashCode()));
        result = ((result* 31)+((this.triggerReason == null)? 0 :this.triggerReason.hashCode()));
        result = ((result* 31)+((this.idToken == null)? 0 :this.idToken.hashCode()));
        result = ((result* 31)+((this.meterValue == null)? 0 :this.meterValue.hashCode()));
        result = ((result* 31)+((this.cableMaxCurrent == null)? 0 :this.cableMaxCurrent.hashCode()));
        result = ((result* 31)+((this.numberOfPhasesUsed == null)? 0 :this.numberOfPhasesUsed.hashCode()));
        result = ((result* 31)+((this.timestamp == null)? 0 :this.timestamp.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TransactionEventRequest rhs)) {
            return false;
        }
        return (((((((((((((Objects.equals(this.seqNo, rhs.seqNo))&&(Objects.equals(this.customData, rhs.customData)))&&(Objects.equals(this.eventType, rhs.eventType)))&&(Objects.equals(this.evse, rhs.evse)))&&(Objects.equals(this.transactionInfo, rhs.transactionInfo)))&&(Objects.equals(this.offline, rhs.offline)))&&(Objects.equals(this.reservationId, rhs.reservationId)))&&(Objects.equals(this.triggerReason, rhs.triggerReason)))&&(Objects.equals(this.idToken, rhs.idToken)))&&(Objects.equals(this.meterValue, rhs.meterValue)))&&(Objects.equals(this.cableMaxCurrent, rhs.cableMaxCurrent)))&&(Objects.equals(this.numberOfPhasesUsed, rhs.numberOfPhasesUsed)))&&(Objects.equals(this.timestamp, rhs.timestamp)));
    }
}
