package com.extrawest.ocpp.emulator.chargepoint.cli.configuration;

import lombok.RequiredArgsConstructor;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

import static com.extrawest.ocpp.emulator.chargepoint.cli.constant.ModelConstants.WS_SUB_PROTOCOL_NAME;

@Configuration
@RequiredArgsConstructor
public class JettyConfiguration {

    @Value("${ocpp.charge-point.jetty.ws-idle-timeout-seconds:129600}") // 1.5 days by default
    private final long webSocketIdleConnectionDuration;

    @Bean(destroyMethod = "stop")
    public WebSocketClient webSocketClient(HttpClient httpClient) throws Exception {
        var webSocketClient = new WebSocketClient(httpClient);
        webSocketClient.setIdleTimeout(Duration.ofSeconds(webSocketIdleConnectionDuration));
        webSocketClient.start();
        return webSocketClient;
    }

    @Bean
    public HttpClient httpClient() {
        var httpClient = new HttpClient();
        httpClient.setMaxRequestsQueuedPerDestination(Integer.MAX_VALUE);
        return httpClient;
    }

    @Bean
    public ClientUpgradeRequest clientUpgradeRequest() {
        var clientUpgradeRequest = new ClientUpgradeRequest();
        clientUpgradeRequest.setSubProtocols(WS_SUB_PROTOCOL_NAME);
        return clientUpgradeRequest;
    }
}
