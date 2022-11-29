package com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime.impl;

import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;

interface UnsupportedByDefaultClientCoreEventHandler extends ClientCoreEventHandler {

    @Override
    default ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {
        return unsupported();
    }

    @Override
    default GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {
        return unsupported();
    }

    @Override
    default ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {
        return unsupported();
    }

    @Override
    default ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {
        return unsupported();
    }

    @Override
    default DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {
        return unsupported();
    }

    @Override
    default RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request) {
        return unsupported();
    }

    @Override
    default RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {
        return unsupported();
    }

    @Override
    default ResetConfirmation handleResetRequest(ResetRequest request) {
        return unsupported();
    }

    @Override
    default UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {
        return unsupported();
    }

    private <T> T unsupported() {
        return null;  // returning null means unsupported feature
    }
}
