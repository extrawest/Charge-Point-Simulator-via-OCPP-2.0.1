package com.extrawest.ocpp.emulator.chargepoint.cli.configuration;

import eu.chargetime.ocpp.ClientEvents;
import eu.chargetime.ocpp.JSONClient;
import eu.chargetime.ocpp.feature.profile.ClientCoreEventHandler;
import eu.chargetime.ocpp.feature.profile.ClientCoreProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EuChargetimeConfiguration {

    @Bean
    public JSONClient jsonClient(ClientCoreProfile coreProfile) {
        return new JSONClient(coreProfile);
    }

    @Bean
    public ClientCoreProfile coreProfile(ClientCoreEventHandler clientCoreEventHandler) {
        return new ClientCoreProfile(clientCoreEventHandler);
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
