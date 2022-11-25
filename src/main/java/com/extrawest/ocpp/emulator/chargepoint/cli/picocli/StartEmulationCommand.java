package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Component
@Command(name = "startEmulationCommand", mixinStandardHelpOptions = true, helpCommand = true)
public class StartEmulationCommand implements Callable<Integer> {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    private String csUrl;
    private int stationCount;

    @Option(names = {"--csUrl", "-C"},
            description = "Specify a url of Central System with 'ws://' schema",
            required = true)
    public void setCentralSystemUrl(String csUrlParameter) {
        boolean invalid = !csUrlParameter.contains("ws://");
        if (invalid) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                    String.format("Invalid value '%s' for option '--csUrl': ", csUrlParameter));
        }
        csUrl = csUrlParameter;
    }

    @Option(names = {"--stationCount", "-S"},
            description = "Specify a count of Charge Points for emulation, value should be integer and greater than zero",
            required = true)
    public void setStationCount (int stationCountParameter) {
        boolean invalid = stationCountParameter <= 0;
        if (invalid) {
                throw new CommandLine.ParameterException(spec.commandLine(),
                        String.format("Invalid value '%s' for option '--stationCount': ", stationCountParameter));
        }
        stationCount = stationCountParameter;
    }

    @Override
    public Integer call() {
        System.out.println("Url: " + csUrl + ", station count: " + stationCount);
        return 23;
    }

}
