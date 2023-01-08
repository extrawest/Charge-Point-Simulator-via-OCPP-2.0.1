package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import com.extrawest.ocpp.emulator.chargepoint.cli.constant.PicocliConstants;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsStartMode;
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

    @Value("${ocpp.emulation.charge-point.stations-count:64500}")
    private int stationCount;

    @Value("${ocpp.emulation.charge-point.logs-count:250}")
    private int connectionCountForLogs;
    private final ChargePointEmulatorsService chargePointEmulatorsService;

    @Value("${ocpp.emulation.charge-point.in-transaction-fraction:0.1}")
    private double chargePointsInTransactionFraction;

    @Value("${ocpp.emulation.start.mode:PARALLEL}")
    private ChargePointsStartMode chargePointsStartMode;

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

    @Option(
        names = {"--stationCount", "-S"},
        description = "@|fg(red)Specify a count of Charge Points for emulation, value should be integer and greater than zero|@"
    )
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

    @Option(
        names = {"--inTransactionFraction", "-T"},
        description = "@|fg(red)Specify a fraction [0; 1] of the charge points to be in an active charging session|@"
    )
    public void setChargePointsInTransactionPercent(double chargePointsInTransactionFraction) {
        boolean invalid = chargePointsInTransactionFraction < 0 || chargePointsInTransactionFraction > 1;
        if (invalid) {
            throw new CommandLine.ParameterException(
                spec.commandLine(),
                String.format("""
                    Invalid value '%s' for option '--inTransactionFraction'. \
                    The fraction is expected to be between 0 and 1 inclusively""",
                    chargePointsInTransactionFraction
                )
            );
        }
        this.chargePointsInTransactionFraction = chargePointsInTransactionFraction;
    }

    @Option(
        names = {"--startMode", "-M"},
        description = "@|fg(red)Specify the start mode. Can be either PARALLEL (default) or SEQUENTIAL|@"
    )
    public void setChargePointsStartMode(ChargePointsStartMode chargePointsStartMode) {
        this.chargePointsStartMode = chargePointsStartMode;
    }

    @Override
    public Integer call() throws EmulationException {
        try {
            chargePointEmulatorsService.startEmulation(
                ChargePointsEmulationParameters.builder()
                    .centralSystemUrl(csUrl)
                    .chargePointsCount(stationCount)
                    .connectionCountForLogs(connectionCountForLogs)
                    .chargePointsInTransactionFraction(chargePointsInTransactionFraction)
                    .chargePointsStartMode(chargePointsStartMode)
                    .build()
            );
        } catch (EmulationConnectionException e) {
            log.warn("Central system unavailable, please check that Central System is alive");
            return PicocliConstants.ERROR_CENTRAL_SYSTEM_UNAVAILABLE;
        }
        return PicocliConstants.SUCCESS;
    }
}
