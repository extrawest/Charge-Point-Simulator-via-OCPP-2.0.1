package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Charging_ Schedule
 * urn:x-oca:ocpp:uid:2:233256
 * Charging schedule structure defines a list of charging periods, as used in: GetCompositeSchedule.conf and ChargingProfile.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "id",
        "startSchedule",
        "duration",
        "chargingRateUnit",
        "chargingSchedulePeriod",
        "minChargingRate",
        "salesTariff"
})
@Builder
@Getter
public class ChargingSchedule {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Identifies the ChargingSchedule.
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    private Integer id;
    /**
     * Charging_ Schedule. Start_ Schedule. Date_ Time
     * urn:x-oca:ocpp:uid:1:569237
     * Starting point of an absolute schedule. If absent the schedule will be relative to start of charging.
     *
     *
     */
    @JsonProperty("startSchedule")
    private Date startSchedule;
    /**
     * Charging_ Schedule. Duration. Elapsed_ Time
     * urn:x-oca:ocpp:uid:1:569236
     * Duration of the charging schedule in seconds. If the duration is left empty, the last period will continue indefinitely or until end of the transaction if chargingProfilePurpose = TxProfile.
     *
     *
     */
    @JsonProperty("duration")
    private Integer duration;
    /**
     * Charging_ Schedule. Charging_ Rate_ Unit. Charging_ Rate_ Unit_ Code
     * urn:x-oca:ocpp:uid:1:569238
     * The unit of measure Limit is expressed in.
     *
     * (Required)
     *
     */
    @JsonProperty("chargingRateUnit")
    private ChargingRateUnitEnum chargingRateUnit;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("chargingSchedulePeriod")
    private List<ChargingSchedulePeriod> chargingSchedulePeriod = null;
    /**
     * Charging_ Schedule. Min_ Charging_ Rate. Numeric
     * urn:x-oca:ocpp:uid:1:569239
     * Minimum charging rate supported by the EV. The unit of measure is defined by the chargingRateUnit. This parameter is intended to be used by a local smart charging algorithm to optimize the power allocation for in the case a charging process is inefficient at lower charging rates. Accepts at most one digit fraction (e.g. 8.1)
     *
     *
     */
    @JsonProperty("minChargingRate")
    private Double minChargingRate;
    /**
     * Sales_ Tariff
     * urn:x-oca:ocpp:uid:2:233272
     * NOTE: This dataType is based on dataTypes from &lt;<ref-ISOIEC15118-2,ISO 15118-2>>.
     *
     *
     */
    @JsonProperty("salesTariff")
    private SalesTariff salesTariff;

}
