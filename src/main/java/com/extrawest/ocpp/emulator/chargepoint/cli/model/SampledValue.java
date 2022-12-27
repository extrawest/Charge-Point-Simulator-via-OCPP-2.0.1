package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampledValue {

    @NonNull
    private final String value;
}
