package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.findByField;


/**
 * Transaction. Stopped_ Reason. EOT_ Reason_ Code
 * urn:x-oca:ocpp:uid:1:569413
 * This contains the reason why the transaction was stopped. MAY only be omitted when Reason is "Local".
 *
 *
 */
public enum ReasonEnum {

    DE_AUTHORIZED("DeAuthorized"),
    EMERGENCY_STOP("EmergencyStop"),
    ENERGY_LIMIT_REACHED("EnergyLimitReached"),
    EV_DISCONNECTED("EVDisconnected"),
    GROUND_FAULT("GroundFault"),
    IMMEDIATE_RESET("ImmediateReset"),
    LOCAL("Local"),
    LOCAL_OUT_OF_CREDIT("LocalOutOfCredit"),
    MASTER_PASS("MasterPass"),
    OTHER("Other"),
    OVERCURRENT_FAULT("OvercurrentFault"),
    POWER_LOSS("PowerLoss"),
    POWER_QUALITY("PowerQuality"),
    REBOOT("Reboot"),
    REMOTE("Remote"),
    SOC_LIMIT_REACHED("SOCLimitReached"),
    STOPPED_BY_EV("StoppedByEV"),
    TIME_LIMIT_REACHED("TimeLimitReached"),
    TIMEOUT("Timeout");
    private final String value;

    ReasonEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @JsonValue
    public String value() {
        return this.value;
    }

    @JsonCreator
    public static ReasonEnum fromValue(String value) {
        return findByField(
                ReasonEnum.class,
                ReasonEnum::value,
                value
        );
    }

}
