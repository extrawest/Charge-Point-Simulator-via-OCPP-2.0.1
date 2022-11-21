package com.extrawest.ocpp.stationemulation.service;

import java.util.concurrent.ExecutionException;

public interface BootNotificationService {
    void setClientSession() throws ExecutionException, InterruptedException;
}
