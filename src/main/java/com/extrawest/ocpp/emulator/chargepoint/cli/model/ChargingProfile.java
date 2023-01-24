package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Charging_ Profile
 * urn:x-oca:ocpp:uid:2:233255
 * A ChargingProfile consists of ChargingSchedule, describing the amount of power or current that can be delivered per time interval.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "id",
        "stackLevel",
        "chargingProfilePurpose",
        "chargingProfileKind",
        "recurrencyKind",
        "validFrom",
        "validTo",
        "chargingSchedule",
        "transactionId"
})
@Builder
@Getter
public class ChargingProfile {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Identified_ Object. MRID. Numeric_ Identifier
     * urn:x-enexis:ecdm:uid:1:569198
     * Id of ChargingProfile.
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    @NotNull
    private Integer id;
    /**
     * Charging_ Profile. Stack_ Level. Counter
     * urn:x-oca:ocpp:uid:1:569230
     * Value determining level in hierarchy stack of profiles. Higher values have precedence over lower values. Lowest level is 0.
     *
     * (Required)
     *
     */
    @JsonProperty("stackLevel")
    @NotNull
    private Integer stackLevel;
    /**
     * Charging_ Profile. Charging_ Profile_ Purpose. Charging_ Profile_ Purpose_ Code
     * urn:x-oca:ocpp:uid:1:569231
     * Defines the purpose of the schedule transferred by this profile
     *
     * (Required)
     *
     */
    @JsonProperty("chargingProfilePurpose")
    @NotNull
    private ChargingProfilePurposeEnum chargingProfilePurpose;
    /**
     * Charging_ Profile. Charging_ Profile_ Kind. Charging_ Profile_ Kind_ Code
     * urn:x-oca:ocpp:uid:1:569232
     * Indicates the kind of schedule.
     *
     * (Required)
     *
     */
    @JsonProperty("chargingProfileKind")
    @NotNull
    private ChargingProfileKindEnum chargingProfileKind;
    /**
     * Charging_ Profile. Recurrency_ Kind. Recurrency_ Kind_ Code
     * urn:x-oca:ocpp:uid:1:569233
     * Indicates the start point of a recurrence.
     *
     *
     */
    @JsonProperty("recurrencyKind")
    private RecurrencyKindEnum recurrencyKind;
    /**
     * Charging_ Profile. Valid_ From. Date_ Time
     * urn:x-oca:ocpp:uid:1:569234
     * Point in time at which the profile starts to be valid. If absent, the profile is valid as soon as it is received by the Charging Station.
     *
     *
     */
    @JsonProperty("validFrom")
    private Date validFrom;
    /**
     * Charging_ Profile. Valid_ To. Date_ Time
     * urn:x-oca:ocpp:uid:1:569235
     * Point in time at which the profile stops to be valid. If absent, the profile is valid until it is replaced by another profile.
     *
     *
     */
    @JsonProperty("validTo")
    @NotNull
    private Date validTo;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("chargingSchedule")
    @NotNull
    private List<ChargingSchedule> chargingSchedule = null;
    /**
     * SHALL only be included if ChargingProfilePurpose is set to TxProfile. The transactionId is used to match the profile to a specific transaction.
     *
     *
     */
    @JsonProperty("transactionId")
    private String transactionId;

}
