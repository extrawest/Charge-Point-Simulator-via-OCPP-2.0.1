package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static picocli.CommandLine.Model.UsageMessageSpec.*;

@Component
public class ChargePointsEmulationRunner implements CommandLineRunner {

    private final StartChargePointsEmulationCommand startChargePointsEmulationCommand;

    private final IFactory factory;

    public ChargePointsEmulationRunner(StartChargePointsEmulationCommand startChargePointsEmulationCommand, IFactory factory) {
        this.startChargePointsEmulationCommand = startChargePointsEmulationCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args) {
        CommandLine commandLine = new CommandLine(startChargePointsEmulationCommand, factory);
        commandLine.getHelpSectionMap().put(SECTION_KEY_FOOTER, new FooterRenderer());
        commandLine.execute(args);
    }
}
