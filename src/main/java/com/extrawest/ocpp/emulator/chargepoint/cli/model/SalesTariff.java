package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;


/**
 * Sales_ Tariff
 * urn:x-oca:ocpp:uid:2:233272
 * NOTE: This dataType is based on dataTypes from &lt;<ref-ISOIEC15118-2,ISO 15118-2>>.
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "id",
        "salesTariffDescription",
        "numEPriceLevels",
        "salesTariffEntry"
})
@Builder
@Getter
public class SalesTariff {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    private CustomData customData;
    /**
     * Identified_ Object. MRID. Numeric_ Identifier
     * urn:x-enexis:ecdm:uid:1:569198
     * SalesTariff identifier used to identify one sales tariff. An SAID remains a unique identifier for one schedule throughout a charging session.
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    private Integer id;
    /**
     * Sales_ Tariff. Sales. Tariff_ Description
     * urn:x-oca:ocpp:uid:1:569283
     * A human readable title/short description of the sales tariff e.g. for HMI display purposes.
     *
     *
     */
    @JsonProperty("salesTariffDescription")
    private String salesTariffDescription;
    /**
     * Sales_ Tariff. Num_ E_ Price_ Levels. Counter
     * urn:x-oca:ocpp:uid:1:569284
     * Defines the overall number of distinct price levels used across all provided SalesTariff elements.
     *
     *
     */
    @JsonProperty("numEPriceLevels")
    private Integer numEPriceLevels;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("salesTariffEntry")
    private List<SalesTariffEntry> salesTariffEntry = null;

}
