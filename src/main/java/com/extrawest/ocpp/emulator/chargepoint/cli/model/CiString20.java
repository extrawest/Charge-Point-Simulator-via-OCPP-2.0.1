package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.ThrowReadablyUtil.unchecked;

@Getter
@ToString
public class CiString20 {

    public static final int VALUE_MAX_LENGTH = 20;

    @JsonValue
    private final String value;

    @JsonCreator
    public CiString20(String value) {
        ensureValueValidOrThrow(value);
        this.value = value;
    }

    private void ensureValueValidOrThrow(String value) {
        if (value != null && value.length() > VALUE_MAX_LENGTH) {
            throw unchecked("Max length of the value must be less then or equal to " + VALUE_MAX_LENGTH);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        CiString20 that = (CiString20) object;
        return (that.getValue() == null && this.getValue() == null)
            || (that.getValue() != null && that.getValue().equalsIgnoreCase(this.getValue()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }
}
