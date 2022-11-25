package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import static picocli.CommandLine.Model.UsageMessageSpec.*;

@Component
public class ChargePointsEmulationRunner implements CommandLineRunner, ExitCodeGenerator {

    private final StartChargePointsEmulationCommand startChargePointsEmulationCommand;

    private final IFactory factory;

    private int exitCode;

    public ChargePointsEmulationRunner(StartChargePointsEmulationCommand startChargePointsEmulationCommand, IFactory factory) {
        this.startChargePointsEmulationCommand = startChargePointsEmulationCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLine commandLine = new CommandLine(startChargePointsEmulationCommand, factory);
        commandLine.getHelpSectionMap().put(SECTION_KEY_FOOTER, new FooterRenderer());
        exitCode = commandLine.execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
