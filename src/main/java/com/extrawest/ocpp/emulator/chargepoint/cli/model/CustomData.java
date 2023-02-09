package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vendorId"
})
@Getter
@EqualsAndHashCode
public class CustomData {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("vendorId")
    public String vendorId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

}
