package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * EVSE
 * urn:x-oca:ocpp:uid:2:233123
 * Electric Vehicle Supply Equipment
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "id",
        "connectorId"
})
@Builder
@Getter
@EqualsAndHashCode
public class EVSE {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Identified_ Object. MRID. Numeric_ Identifier
     * urn:x-enexis:ecdm:uid:1:569198
     * EVSE Identifier. This contains a number (&gt; 0) designating an EVSE of the Charging Station.
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    public Integer id;
    /**
     * An id to designate a specific connector (on an EVSE) by connector index number.
     *
     *
     */
    @JsonProperty("connectorId")
    public Integer connectorId;

}
