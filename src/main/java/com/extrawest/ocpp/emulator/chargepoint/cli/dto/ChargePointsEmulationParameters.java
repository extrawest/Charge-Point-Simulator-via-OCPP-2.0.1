package com.extrawest.ocpp.emulator.chargepoint.cli.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
@Builder
public class ChargePointsEmulationParameters {

    @NotBlank
    private final String centralSystemUrl;

    @Min(1)
    private final int chargePointsCount;

    @Min(1)
    private final int connectionCountForLogs;

    @Min(0)
    @Max(1)
    private final double chargePointsInTransactionFraction;

    @NotNull
    @NonNull
    private final ChargePointsStartMode chargePointsStartMode;
}
