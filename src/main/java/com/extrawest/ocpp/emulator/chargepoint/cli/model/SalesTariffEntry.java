package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Sales_ Tariff_ Entry
 * urn:x-oca:ocpp:uid:2:233271
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "relativeTimeInterval",
        "ePriceLevel",
        "consumptionCost"
})
@Builder
@Getter
public class SalesTariffEntry {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Relative_ Timer_ Interval
     * urn:x-oca:ocpp:uid:2:233270
     *
     * (Required)
     *
     */
    @JsonProperty("relativeTimeInterval")
    private RelativeTimeInterval relativeTimeInterval;
    /**
     * Sales_ Tariff_ Entry. E_ Price_ Level. Unsigned_ Integer
     * urn:x-oca:ocpp:uid:1:569281
     * Defines the price level of this SalesTariffEntry (referring to NumEPriceLevels). Small values for the EPriceLevel represent a cheaper TariffEntry. Large values for the EPriceLevel represent a more expensive TariffEntry.
     *
     *
     */
    @JsonProperty("ePriceLevel")
    private Integer ePriceLevel;
    @JsonProperty("consumptionCost")
    private List<ConsumptionCost> consumptionCost = null;

}
