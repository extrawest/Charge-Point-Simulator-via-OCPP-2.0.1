package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.BootReasonEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.ChargingStation;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "chargingStation",
        "reason"
})
@Builder
@Getter
public class BootNotificationRequest {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Charge_ Point
     * urn:x-oca:ocpp:uid:2:233122
     * The physical system where an Electrical Vehicle (EV) can be charged.
     *
     * (Required)
     *
     */
    @JsonProperty("chargingStation")
    @NotNull
    public ChargingStation chargingStation;
    /**
     * This contains the reason for sending this message to the CSMS.
     *
     * (Required)
     *
     */
    @JsonProperty("reason")
    public BootReasonEnum reason;

}
