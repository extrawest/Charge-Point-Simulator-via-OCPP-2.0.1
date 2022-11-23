package com.extrawest.ocpp.emulator.chargepoint.cli.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class ChargePointsEmulationParameters {

    @Min(1)
    private long chargePointsAmount;

    @NotBlank
    private String centralSystemUrl;
}
