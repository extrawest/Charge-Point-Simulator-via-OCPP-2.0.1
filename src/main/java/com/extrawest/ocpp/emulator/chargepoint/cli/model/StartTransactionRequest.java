package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@ToString
public class StartTransactionRequest {

    private final int connectorId;

    @NonNull
    private final IdToken idTag;

    private final int meterStart;

    private final LocalDateTime timestamp;
}
