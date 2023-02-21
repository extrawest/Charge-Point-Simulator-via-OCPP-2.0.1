package com.extrawest.ocpp.emulator.chargepoint.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import static com.extrawest.ocpp.emulator.chargepoint.cli.util.EnumUtil.*;


/**
 * Certificate status information.
 * - if all certificates are valid: return 'Accepted'.
 * - if one of the certificates was revoked, return 'CertificateRevoked'.
 */
public enum AuthorizeCertificateStatusEnum {

    ACCEPTED("Accepted"),
    SIGNATURE_ERROR("SignatureError"),
    CERTIFICATE_EXPIRED("CertificateExpired"),
    CERTIFICATE_REVOKED("CertificateRevoked"),
    NO_CERTIFICATE_AVAILABLE("NoCertificateAvailable"),
    CERT_CHAIN_ERROR("CertChainError"),
    CONTRACT_CANCELLED("ContractCancelled");
    private final String value;

    AuthorizeCertificateStatusEnum(String value) {
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
    public static AuthorizeCertificateStatusEnum fromValue(String value) {
        return findByField(
                AuthorizeCertificateStatusEnum.class,
                AuthorizeCertificateStatusEnum::value,
                value
        );
    }

}
