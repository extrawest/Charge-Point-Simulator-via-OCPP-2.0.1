package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Component
@Command(name = "startEmulationCommand", mixinStandardHelpOptions = true, helpCommand = true)
public class StartChargePointsEmulationCommand implements Callable<Integer> {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    private String csUrl;
    private int stationCount;
    private final ChargePointEmulatorsService chargePointEmulatorsService;

    public StartChargePointsEmulationCommand(@Autowired ChargePointEmulatorsService chargePointEmulatorsService) {
        this.chargePointEmulatorsService = chargePointEmulatorsService;
    }

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
        chargePointEmulatorsService.startEmulation(new ChargePointsEmulationParameters(csUrl, stationCount));
        System.out.println("Url: " + csUrl + ", station count: " + stationCount);
        return 0;
    }

}
