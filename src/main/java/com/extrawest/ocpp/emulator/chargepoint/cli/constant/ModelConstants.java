package com.extrawest.ocpp.emulator.chargepoint.cli.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelConstants {

    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final int DEFAULT_CONNECTOR_ID = 1;

    public static final String SEC_WEBSOCKET_PROTOCOL_HEADER_NAME = "Sec-WebSocket-Protocol";

    public static final String SEC_WEBSOCKET_PROTOCOL_HEADER_VALUE = "ocpp1.6";
}
