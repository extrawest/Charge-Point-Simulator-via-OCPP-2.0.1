package com.extrawest.ocpp.emulator.chargepoint.cli.euchargetime.impl;

import eu.chargetime.ocpp.ClientEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientEventsImpl implements ClientEvents {

    @Override
    public void connectionOpened() {
        log.info("ClientEvents - connectionOpened()");
    }

    @Override
    public void connectionClosed() {
        log.info("ClientEvents - connectionClosed()");
    }
}
