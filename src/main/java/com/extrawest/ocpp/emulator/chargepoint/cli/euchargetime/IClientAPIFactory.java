package com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime;

import eu.chargetime.ocpp.IClientAPI;

public interface IClientAPIFactory {

    IClientAPI createNewClientApi();
}
