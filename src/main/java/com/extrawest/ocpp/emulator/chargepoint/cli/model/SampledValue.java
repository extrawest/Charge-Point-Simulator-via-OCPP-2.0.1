package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SampledValue {

    @NonNull
    private final String value;
}
