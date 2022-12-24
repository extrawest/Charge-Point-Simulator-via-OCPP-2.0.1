package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import com.extrawest.ocpp.emulator.chargepoint.cli.constant.PicocliConstants;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationConnectionException;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Component
@Command(name = "startEmulationCommand", mixinStandardHelpOptions = true, helpCommand = true)
@Slf4j
public class StartChargePointsEmulationCommand implements Callable<Integer> {
    @CommandLine.Spec
    CommandLine.Model.CommandSpec spec;
    private String csUrl;
    private int stationCount;

    @Value("${ocpp.charge-point.logs-count:200}")
    private int connectionCountForLogs;
    private final ChargePointEmulatorsService chargePointEmulatorsService;

    public StartChargePointsEmulationCommand(@Autowired ChargePointEmulatorsService chargePointEmulatorsService) {
        this.chargePointEmulatorsService = chargePointEmulatorsService;
    }

    @Option(names = {"--csUrl", "-C"},
        description = "Specify a @|bold,underline url|@ of Central System with @|fg(red) 'ws://'|@ schema",
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
        description = "@|fg(red)Specify a count of Charge Points for emulation, value should be integer and greater than zero|@",
        required = true)
    public void setStationCount (int stationCountParameter) {
        boolean invalid = stationCountParameter <= 0;
        if (invalid) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                String.format("Invalid value '%s' for option '--stationCount'", stationCountParameter));
        }
        stationCount = stationCountParameter;
    }

    @Option(names = {"--logsRange", "-L"}, description = "@|fg(red)Specify a range step for logging|@")
    public void setConnectionCountForLogs(int connectionCountForLogsParameter) {
        boolean invalid = connectionCountForLogsParameter <= 0;
        if (invalid) {
            throw new CommandLine.ParameterException(spec.commandLine(),
                String.format("Invalid value '%s' for option '--logsRange'", connectionCountForLogsParameter));
        }
        connectionCountForLogs = connectionCountForLogsParameter;
    }

    @Override
    public Integer call() throws EmulationException {
        try {
            chargePointEmulatorsService.startEmulation(
                new ChargePointsEmulationParameters(csUrl, stationCount, connectionCountForLogs)
            );
        } catch (EmulationConnectionException e) {
            log.warn("Central system unavailable, please check that Central System is alive");
            return PicocliConstants.ERROR_CENTRAL_SYSTEM_UNAVAILABLE;
        }
        return PicocliConstants.SUCCESS;
    }
}
