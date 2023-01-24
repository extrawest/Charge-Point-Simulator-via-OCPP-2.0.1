package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Charge_ Point
 * urn:x-oca:ocpp:uid:2:233122
 * The physical system where an Electrical Vehicle (EV) can be charged.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "serialNumber",
        "model",
        "modem",
        "vendorName",
        "firmwareVersion"
})
@Builder
@Getter
public class ChargingStation {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Device. Serial_ Number. Serial_ Number
     * urn:x-oca:ocpp:uid:1:569324
     * Vendor-specific device identifier.
     *
     *
     */
    @JsonProperty("serialNumber")
    public String serialNumber;
    /**
     * Device. Model. CI20_ Text
     * urn:x-oca:ocpp:uid:1:569325
     * Defines the model of the device.
     *
     * (Required)
     *
     */
    @JsonProperty("model")
    public String model;
    /**
     * Wireless_ Communication_ Module
     * urn:x-oca:ocpp:uid:2:233306
     * Defines parameters required for initiating and maintaining wireless communication with other devices.
     *
     *
     */
    @JsonProperty("modem")
    public Modem modem;
    /**
     * Identifies the vendor (not necessarily in a unique manner).
     *
     * (Required)
     *
     */
    @JsonProperty("vendorName")
    public String vendorName;
    /**
     * This contains the firmware version of the Charging Station.
     *
     *
     *
     */
    @JsonProperty("firmwareVersion")
    public String firmwareVersion;

}
