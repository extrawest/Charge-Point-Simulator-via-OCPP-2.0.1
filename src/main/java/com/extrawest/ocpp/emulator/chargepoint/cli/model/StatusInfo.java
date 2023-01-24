package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Element providing more information about the status.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "reasonCode",
        "additionalInfo"
})
@Builder
@Getter
public class StatusInfo {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * A predefined code for the reason why the status is returned in this response. The string is case-insensitive.
     *
     * (Required)
     *
     */
    @JsonProperty("reasonCode")
    private String reasonCode;
    /**
     * Additional text to provide detailed information.
     *
     *
     */
    @JsonProperty("additionalInfo")
    private String additionalInfo;
}
