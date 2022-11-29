package com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime.IClientAPIFactory;
import eu.chargetime.ocpp.IClientAPI;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JSONClientFactory implements IClientAPIFactory {

    private final ClientCoreProfile coreProfile;

    @Override
    public IClientAPI createNewClientApi() {
        return new JSONClient(coreProfile);
    }
}
