package com.extrawest.ocpp.emulator.chargepoint.cli.model.payload;

import com.extrawest.ocpp.emulator.chargepoint.cli.model.CustomData;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.RegistrationStatusEnum;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.StatusInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customData",
        "currentTime",
        "interval",
        "status",
        "statusInfo"
})
@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class BootNotificationResponse {

    /**
     * This class does not get 'AdditionalProperties = false' in the schema generation, so it can be extended with arbitrary JSON properties to allow adding custom data.
     *
     */
    @JsonProperty("customData")
    public CustomData customData;
    /**
     * This contains the CSMS’s current time.
     *
     * (Required)
     *
     */
    @JsonProperty("currentTime")
    public Date currentTime;
    /**
     * When &lt;<cmn_registrationstatusenumtype,Status>> is Accepted, this contains the heartbeat interval in seconds. If the CSMS returns something other than Accepted, the value of the interval field indicates the minimum wait time before sending a next BootNotification request.
     *
     * (Required)
     *
     */
    @JsonProperty("interval")
    public Integer interval;
    /**
     * This contains whether the Charging Station has been registered
     * within the CSMS.
     *
     * (Required)
     *
     */
    @JsonProperty("status")
    public RegistrationStatusEnum status;
    /**
     * Element providing more information about the status.
     *
     *
     */
    @JsonProperty("statusInfo")
    public StatusInfo statusInfo;

}
