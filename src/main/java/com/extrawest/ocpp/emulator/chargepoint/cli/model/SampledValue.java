package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;


/**
 * Sampled_ Value
 * urn:x-oca:ocpp:uid:2:233266
 * Single sampled value in MeterValues. Each value can be accompanied by optional fields.
 *
 * To save on mobile data usage, default values of all of the optional fields are such that. The value without any additional fields will be interpreted, as a register reading of active import energy in Wh (Watt-hour) units.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "value",
        "context",
        "measurand",
        "phase",
        "location",
        "signedMeterValue",
        "unitOfMeasure"
})
public class SampledValue {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * Sampled_ Value. Value. Measure
     * urn:x-oca:ocpp:uid:1:569260
     * Indicates the measured value.
     *
     *
     * (Required)
     *
     */
    @JsonProperty("value")
    public Double value;
    /**
     * Sampled_ Value. Context. Reading_ Context_ Code
     * urn:x-oca:ocpp:uid:1:569261
     * Type of detail value: start, end or sample. Default = "Sample.Periodic"
     *
     *
     */
    @JsonProperty("context")
    public ReadingContextEnum context = ReadingContextEnum.fromValue("Sample.Periodic");
    /**
     * Sampled_ Value. Measurand. Measurand_ Code
     * urn:x-oca:ocpp:uid:1:569263
     * Type of measurement. Default = "Energy.Active.Import.Register"
     *
     *
     */
    @JsonProperty("measurand")
    public MeasurandEnum measurand = MeasurandEnum.ENERGY_ACTIVE_IMPORT_REGISTER;
    /**
     * Sampled_ Value. Phase. Phase_ Code
     * urn:x-oca:ocpp:uid:1:569264
     * Indicates how the measured value is to be interpreted. For instance between L1 and neutral (L1-N) Please note that not all values of phase are applicable to all Measurands. When phase is absent, the measured value is interpreted as an overall value.
     *
     *
     */
    @JsonProperty("phase")
    public PhaseEnum phase;
    /**
     * Sampled_ Value. Location. Location_ Code
     * urn:x-oca:ocpp:uid:1:569265
     * Indicates where the measured value has been sampled. Default = "Outlet"
     *
     *
     *
     */
    @JsonProperty("location")
    public LocationEnum location = LocationEnum.fromValue("Outlet");
    /**
     * Represent a signed version of the meter value.
     *
     *
     */
    @JsonProperty("signedMeterValue")
    public SignedMeterValue signedMeterValue;
    /**
     * Represents a UnitOfMeasure with a multiplier
     *
     *
     */
    @JsonProperty("unitOfMeasure")
    public UnitOfMeasure unitOfMeasure;

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.phase == null)? 0 :this.phase.hashCode()));
        result = ((result* 31)+((this.signedMeterValue == null)? 0 :this.signedMeterValue.hashCode()));
        result = ((result* 31)+((this.unitOfMeasure == null)? 0 :this.unitOfMeasure.hashCode()));
        result = ((result* 31)+((this.context == null)? 0 :this.context.hashCode()));
        result = ((result* 31)+((this.measurand == null)? 0 :this.measurand.hashCode()));
        result = ((result* 31)+((this.customData == null)? 0 :this.customData.hashCode()));
        result = ((result* 31)+((this.location == null)? 0 :this.location.hashCode()));
        result = ((result* 31)+((this.value == null)? 0 :this.value.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SampledValue rhs)) {
            return false;
        }
        return Objects.equals(this.phase, rhs.phase) && Objects.equals(this.signedMeterValue, rhs.signedMeterValue) && Objects.equals(this.unitOfMeasure, rhs.unitOfMeasure) && Objects.equals(this.context, rhs.context) && this.measurand == rhs.measurand && (Objects.equals(this.customData, rhs.customData)) && (Objects.equals(this.location, rhs.location)) && (Objects.equals(this.value, rhs.value));
    }

}
