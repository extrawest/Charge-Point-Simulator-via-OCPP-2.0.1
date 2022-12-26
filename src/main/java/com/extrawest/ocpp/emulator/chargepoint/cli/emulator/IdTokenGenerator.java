package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;

public interface IdTokenGenerator {

    IdToken generateRandomToken();
}
