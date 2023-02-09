package com.extrawest.ocpp.emulator.chargepoint.cli.service.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.dto.ChargePointsEmulationParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.dto.CreateChargePointParameters;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulator;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.ChargePointEmulatorFactory;
import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.action.*;
import com.extrawest.ocpp.emulator.chargepoint.cli.event.ConfigurableMultipleEventHandler;
import com.extrawest.ocpp.emulator.chargepoint.cli.exception.emulator.EmulationException;
import com.extrawest.ocpp.emulator.chargepoint.cli.service.ChargePointEmulatorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.stream.LongStream;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class ChargePointEmulatorsServiceImpl implements ChargePointEmulatorsService {

    @Qualifier("ExecutorService")
    private final ExecutorService executorService;

    private final ChargePointEmulatorFactory chargePointEmulatorFactory;

    @Value("${ocpp.charge-point.id-index-prefix:CP}")
    private final String chargePointIdIndexPrefix;

    private final CentralSystemConnectAction connectAction;

    private final SendBootNotificationAction sendBootNotificationAction;

    private final StartHeartbeatingAction startHeartbeatingAction;

    private final StopHeartbeatingAction stopHeartbeatingAction;

    private final SendAuthorizeAction sendAuthorizeAction;

    private final SendStartTransactionAction sendStartTransactionAction;

    private final StartSendingMeterValuesAction startSendingMeterValuesAction;

    private final List<ConfigurableMultipleEventHandler> configurableMultipleEventHandlers;

    @Override
    public void startEmulation(@Valid ChargePointsEmulationParameters parameters) throws EmulationException {
        log.info("Trying to start the emulation");
        tryCreateAndStartEmulatorsOrThrow(parameters);
    }

    private void tryCreateAndStartEmulatorsOrThrow(ChargePointsEmulationParameters parameters)
        throws EmulationException {
        try {
            createAndStartEmulators(parameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new EmulationException(e);
        }
    }

    private void createAndStartEmulators(ChargePointsEmulationParameters parameters) {
        Optional.of(parameters)
            .map(this::createChargePointEmulators)
            .ifPresent(chargePointEmulators -> startChargePointEmulators(chargePointEmulators, parameters));
    }

    private List<ChargePointEmulator> createChargePointEmulators(ChargePointsEmulationParameters parameters) {
        return LongStream.range(0, parameters.getChargePointsCount())
            .parallel()
            .mapToObj(i -> new CreateChargePointParameters(
                parameters.getCentralSystemUrl(), createChargePointIdForIndex(i)
            ))
            .map(chargePointEmulatorFactory::createChargePointEmulator)
            .toList();
    }

    private void startChargePointEmulators(
        List<ChargePointEmulator> chargePointEmulators, ChargePointsEmulationParameters parameters
    ) {
        log.info("Starting " + chargePointEmulators.size() + " emulators");
        setupMultipleEventCounters(parameters);

        var bootEmulatorAndStartHeartbeatingAction = connectAction
                .andThen(sendBootNotificationAction)
                .andThen(startHeartbeatingAction);

        var stopHeartbeatingAndStartTransactionAction = stopHeartbeatingAction
                .andThen(sendAuthorizeAction)
                .andThen(sendStartTransactionAction)
                .andThen(startSendingMeterValuesAction);

        switch (parameters.getChargePointsStartMode()) {
            case PARALLEL -> startChargePointEmulatorsParallel(
                chargePointEmulators,
                parameters,
                bootEmulatorAndStartHeartbeatingAction,
                stopHeartbeatingAndStartTransactionAction
            );
            case SEQUENTIAL -> startChargePointEmulatorsSequential(
                chargePointEmulators,
                parameters,
                bootEmulatorAndStartHeartbeatingAction,
                stopHeartbeatingAndStartTransactionAction
            );
        }

        log.info(chargePointEmulators.size() + " charge points emulators were created");
    }

    private void startChargePointEmulatorsParallel(
        List<ChargePointEmulator> chargePointEmulators,
        ChargePointsEmulationParameters parameters,
        Consumer<ChargePointEmulator> bootEmulatorAndStartHeartbeatingAction,
        Consumer<ChargePointEmulator> stopHeartbeatingAndStartTransactionAction
    ) {
        chargePointEmulators.stream()
            .map(emulator -> (Runnable) () -> bootEmulatorAndStartHeartbeatingAction.accept(emulator))
            .map(executorService::submit)
            .toList()
            .forEach(this::tryWaitForResult);

        chargePointEmulators.stream()
            .limit((long) (chargePointEmulators.size() * parameters.getChargePointsInTransactionFraction()))
            .map(emulator -> (Runnable) () -> stopHeartbeatingAndStartTransactionAction.accept(emulator))
            .map(executorService::submit)
            .toList()
            .forEach(this::tryWaitForResult);
    }

    private void startChargePointEmulatorsSequential(
        List<ChargePointEmulator> chargePointEmulators,
        ChargePointsEmulationParameters parameters,
        Consumer<ChargePointEmulator> bootEmulatorAndStartHeartbeatingAction,
        Consumer<ChargePointEmulator> stopHeartbeatingAndStartTransactionAction
    ) {
        chargePointEmulators.forEach(bootEmulatorAndStartHeartbeatingAction);
        chargePointEmulators.stream()
            .limit((long) (chargePointEmulators.size() * parameters.getChargePointsInTransactionFraction()))
            .forEach(stopHeartbeatingAndStartTransactionAction);
    }

    private void tryWaitForResult(Future<?> future) {
        try {
            future.get();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String createChargePointIdForIndex(long chargePointIndex) {
        return chargePointIdIndexPrefix + chargePointIndex;
    }

    private void setupMultipleEventCounters(ChargePointsEmulationParameters parameters) {
        configurableMultipleEventHandlers.forEach(
            configurableMultipleEventHandler -> configurableMultipleEventHandler.setHandleMultiple(
                parameters.getConnectionCountForLogs()
            )
        );
    }
}
