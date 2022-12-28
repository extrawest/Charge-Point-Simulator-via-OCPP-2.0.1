package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class StopTransactionRequest {

    private final int meterStop;

    private final LocalDateTime timestamp;

    private final int transactionId;
}
