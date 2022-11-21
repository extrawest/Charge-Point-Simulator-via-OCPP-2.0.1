package com.extrawest.ocpp.stationemulation.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Service
public class CustomWebSocketHandler extends TextWebSocketHandler {
    Logger logger = LoggerFactory.getLogger(CustomWebSocketHandler.class);

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("Text message handled, payload: " + message.getPayload());
    }
}
