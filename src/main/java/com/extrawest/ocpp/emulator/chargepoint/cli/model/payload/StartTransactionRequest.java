package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.DATE_FORMAT;

@RequiredArgsConstructor
@ToString
@Getter
public class StartTransactionRequest {

    private final int connectorId;

    @NonNull
    private final IdToken idTag;

    private final int meterStart;

    @JsonFormat(pattern = DATE_FORMAT)
    private final LocalDateTime timestamp;
}
