package com.extrawest.ocpp.emulator.chargepoint.cli.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Configuration
@RequiredArgsConstructor
public class ExecutorConfiguration {

    @Value("${emulation.scheduling.thread-pool.core-size:20}")
    private final int schedulingPoolCoreSize;

    @Bean(destroyMethod = "shutdownNow")
    public ScheduledThreadPoolExecutor executor() {
        return new ScheduledThreadPoolExecutor(schedulingPoolCoreSize);
    }
}
