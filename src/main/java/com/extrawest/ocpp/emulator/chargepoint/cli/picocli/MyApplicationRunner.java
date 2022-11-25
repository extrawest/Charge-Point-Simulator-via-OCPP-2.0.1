package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import picocli.CommandLine;
import picocli.CommandLine.IFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import static picocli.CommandLine.Model.UsageMessageSpec.*;

@Component
public class MyApplicationRunner implements CommandLineRunner, ExitCodeGenerator {

    private final StartEmulationCommand startEmulationCommand;

    private final IFactory factory; // auto-configured to inject PicocliSpringFactory

    private int exitCode;

    public MyApplicationRunner(StartEmulationCommand startEmulationCommand, IFactory factory) {
        this.startEmulationCommand = startEmulationCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args) throws Exception {
        CommandLine commandLine = new CommandLine(startEmulationCommand, factory);
        commandLine.getHelpSectionMap().put(SECTION_KEY_FOOTER, new FooterRenderer());
        exitCode = commandLine.execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
