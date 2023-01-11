package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.WebsocketEventListener;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.function.Consumer;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Slf4j
@Component
@RequiredArgsConstructor
public class CentralSystemConnectAction implements Consumer<ChargePointEmulator> {

    private final WebsocketEventListener websocketEventListener;

    @Override
    public void accept(ChargePointEmulator chargePointEmulator) {
        var centralSystemClient = chargePointEmulator.getCentralSystemClient();
        var connectionUrl = URI.create(chargePointEmulator.getCentralSystemUrl())
            .resolve(chargePointEmulator.getChargePointId());
        try {
            centralSystemClient.connect(connectionUrl);
        } catch (EmulationException e) {
            websocketEventListener.onConnectionFailed();
            throw unchecked(e);
        }
    }
}
