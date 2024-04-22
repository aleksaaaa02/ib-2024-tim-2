package com.bookify.pki.enumerations;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.Extension;

public enum ExtensionsType {
    KEY_USAGE(Extension.keyUsage),
    EXTENDED_KEY_USAGE(Extension.extendedKeyUsage),
    SUBJECT_ALTERNATIVE_NAME(Extension.subjectAlternativeName),
    BASIC_CONSTRAINTS(Extension.basicConstraints);

    private final ASN1ObjectIdentifier extension;
    ExtensionsType(ASN1ObjectIdentifier extension){
        this.extension = extension;
    }

    public ASN1ObjectIdentifier getOID(){
        return this.extension;
    }

}
