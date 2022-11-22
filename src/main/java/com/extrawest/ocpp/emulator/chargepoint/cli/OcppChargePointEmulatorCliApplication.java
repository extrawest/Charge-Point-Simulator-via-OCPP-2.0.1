package com.extrawest.ocpp.emulator.chargepoint.cli;

import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import eu.chargetime.ocpp.model.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class OcppChargePointEmulatorCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcppChargePointEmulatorCliApplication.class, args);
    }

    @Bean
    public ClientEvents clientEvents() {
        return new ClientEvents() {
            @Override
            public void connectionOpened() {
                log.info("ClientEvents - connectionOpened()");
            }

            @Override
            public void connectionClosed() {
                log.info("ClientEvents - connectionClosed()");
            }
        };
    }

    @Bean
    public JSONClient jsonClient(ClientCoreProfile coreProfile) {
        return new JSONClient(coreProfile);
    }

    @Bean
    public ClientCoreProfile coreProfile(ClientCoreEventHandler clientCoreEventHandler) {
        return new ClientCoreProfile(clientCoreEventHandler);
    }

    @Bean
    public ClientCoreEventHandler clientCoreEventHandler() {
        return new ClientCoreEventHandler() {
            @Override
            public ChangeAvailabilityConfirmation handleChangeAvailabilityRequest(ChangeAvailabilityRequest request) {
                log.debug("ChangeAvailabilityRequest: " + request);
                return unsupported();
            }

            @Override
            public GetConfigurationConfirmation handleGetConfigurationRequest(GetConfigurationRequest request) {
                log.debug("GetConfigurationRequest: " + request);
                return unsupported();
            }

            @Override
            public ChangeConfigurationConfirmation handleChangeConfigurationRequest(ChangeConfigurationRequest request) {
                log.debug("ChangeConfigurationRequest: " + request);
                return unsupported();
            }

            @Override
            public ClearCacheConfirmation handleClearCacheRequest(ClearCacheRequest request) {
                log.debug("ClearCacheRequest: " + request);
                return unsupported();
            }

            @Override
            public DataTransferConfirmation handleDataTransferRequest(DataTransferRequest request) {
                log.debug("DataTransferRequest: " + request);
                return unsupported();
            }

            @Override
            public RemoteStartTransactionConfirmation handleRemoteStartTransactionRequest(RemoteStartTransactionRequest request) {
                log.debug("RemoteStartTransactionRequest: " + request);
                return unsupported();
            }

            @Override
            public RemoteStopTransactionConfirmation handleRemoteStopTransactionRequest(RemoteStopTransactionRequest request) {
                log.debug("RemoteStopTransactionRequest: " + request);
                return unsupported();
            }

            @Override
            public ResetConfirmation handleResetRequest(ResetRequest request) {
                log.debug("ResetRequest: " + request);
                return unsupported();
            }

            @Override
            public UnlockConnectorConfirmation handleUnlockConnectorRequest(UnlockConnectorRequest request) {
                log.debug("UnlockConnectorRequest: " + request);
                return unsupported();
            }

            private <T> T unsupported() {
                return null;  // returning null means unsupported feature
            }
        };
    }

    @Bean
    public CommandLineRunner commandLineRunner(
        JSONClient jsonClient,
        ClientCoreProfile coreProfile,
        @Value("${ocpp.cms.url}") String ocppCmsUrl,
        @Value("${ocpp.charge-point.boot-notification.chargePointModel}") String chargePointModel,
        @Value("${ocpp.charge-point.boot-notification.chargePointVendor}") String chargePointVendor,
        ClientEvents clientEvents
    ) {
        return args -> {
            jsonClient.connect(ocppCmsUrl, clientEvents);
            jsonClient.send(coreProfile.createBootNotificationRequest(chargePointVendor, chargePointModel));
            jsonClient.send(coreProfile.createHeartbeatRequest());
            jsonClient.disconnect();
        };
    }
}
