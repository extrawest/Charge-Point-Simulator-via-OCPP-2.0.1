package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorizeRequest {


    @NonNull
    private final IdToken idTag;
}
