package com.extrawest.ocpp.emulator.chargepoint.cli.emulator.impl;

import com.extrawest.ocpp.emulator.chargepoint.cli.emulator.IdTokenGenerator;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.CiString20;
import com.extrawest.ocpp.emulator.chargepoint.cli.model.IdToken;
import org.springframework.stereotype.Component;

@Component
public class IdTokenGeneratorImpl implements IdTokenGenerator {

    @Override
    public IdToken generateRandomToken() {
        return new IdToken(generateRandomCiString20());
    }

    private CiString20 generateRandomCiString20() {
        return new CiString20(generateRandomAlphanumericString(CiString20.VALUE_MAX_LENGTH));
    }

    private String generateRandomAlphanumericString(int length) {
        var alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        var stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomCharacter = alphanumericCharacters.charAt(
                (int) (alphanumericCharacters.length() * Math.random())
            );
            stringBuilder.append(randomCharacter);
        }
        return stringBuilder.toString();
    }
}
