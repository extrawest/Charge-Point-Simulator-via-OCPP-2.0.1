package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationConnectionException;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;

import java.net.URI;

public interface CentralSystemClient {

    void connect(URI centralSystemUri) throws EmulationConnectionException;

    void disconnect();

    <T, U> CallResult<U> sendCall(Call<T> call, Class<U> expectedCallResultClass);
}
