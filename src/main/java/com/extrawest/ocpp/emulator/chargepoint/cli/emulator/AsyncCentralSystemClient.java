package com.extrawest.ocpp.emulator.chargepoint.cli.emulator;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.Call;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.call.CallResult;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface AsyncCentralSystemClient extends CentralSystemClient {

    Future<?> connectAsync(URI centralSystemUri);

    <T, U> CompletableFuture<CallResult<U>> sendCallAsync(Call<T> call, Class<U> expectedCallResultClass);
}
