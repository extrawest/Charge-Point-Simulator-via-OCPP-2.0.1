package com.extrawest.ocpp.emulator.chargepoint.cli.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfiguration {

    @Value("${ocpp.emulation.scheduling.thread-pool.core-size:10}")
    private final int schedulingPoolCoreSize;

    @Bean(destroyMethod = "shutdownNow")
    public ScheduledExecutorService executor() {
        return Executors.newScheduledThreadPool(schedulingPoolCoreSize);
    }
}
