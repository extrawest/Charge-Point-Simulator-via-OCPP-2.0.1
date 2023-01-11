package com.extrawest.ocpp.emulator.chargepoint.cli.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfiguration {

    @Value("${ocpp.emulation.scheduling.thread-pool.core-size:48}")
    private final int scheduledExecutorServiceCorePoolSize;

    @Value("${ocpp.emulation.start.thread-pool.size:32}")
    private final int executorServicePoolSize;

    @Bean(destroyMethod = "shutdownNow", name = "ScheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(scheduledExecutorServiceCorePoolSize);
    }

    @Bean(destroyMethod = "shutdownNow", name = "ExecutorService")
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(executorServicePoolSize);
    }
}
