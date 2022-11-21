package com.extrawest.ocpp.stationemulation.service.impl;

import com.extrawest.ocpp.stationemulation.handler.CustomWebSocketHandler;
import com.extrawest.ocpp.stationemulation.service.BootNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BootNotificationServiceImpl implements BootNotificationService {
    private WebSocketSession webSocketSession;
    private String centralSystemUrl = "ws://1b2d-194-104-23-185.eu.ngrok.io/charge/websocket/CentralSystemService/EH289868";

    @Override
    public void setClientSession() throws ExecutionException, InterruptedException {
        this.webSocketSession = new StandardWebSocketClient().doHandshake(
                new CustomWebSocketHandler(), new WebSocketHttpHeaders(), URI.create(centralSystemUrl)
        ).get();
    }
}
