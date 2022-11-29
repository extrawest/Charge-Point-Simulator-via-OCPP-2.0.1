package com.extrawest.ocpp.emulator.chargepoint.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class OcppChargePointEmulatorCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcppChargePointEmulatorCliApplication.class, args);
    }
}
