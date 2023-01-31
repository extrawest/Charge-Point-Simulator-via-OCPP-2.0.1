package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;


/**
 * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vendorId"
})
@Builder
@Getter
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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.vendorId == null)? 0 :this.vendorId.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CustomData rhs)) {
            return false;
        }
        return ((Objects.equals(this.vendorId, rhs.vendorId))&&(Objects.equals(this.additionalProperties, rhs.additionalProperties)));
    }
}
