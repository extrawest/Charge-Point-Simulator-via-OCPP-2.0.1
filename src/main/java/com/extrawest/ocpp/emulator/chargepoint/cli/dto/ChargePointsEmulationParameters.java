package com.extrawest.ocpp.emulator.chargepoint.cli.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ChargePointsEmulationParameters {

    @NotBlank
    private String centralSystemUrl;

    @Min(1)
    private long chargePointsCount;
}
