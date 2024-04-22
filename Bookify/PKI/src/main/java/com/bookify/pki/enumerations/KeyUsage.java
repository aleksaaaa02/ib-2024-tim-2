package com.bookify.pki.enumerations;

public enum KeyUsage {
    DIGITAL_SIGNATURE(org.bouncycastle.asn1.x509.KeyUsage.digitalSignature),
    NON_REPUDIATION(org.bouncycastle.asn1.x509.KeyUsage.nonRepudiation),
    KEY_ENCIPHERMENT(org.bouncycastle.asn1.x509.KeyUsage.keyEncipherment),
    DATA_ENCIPHERMENT(org.bouncycastle.asn1.x509.KeyUsage.dataEncipherment),
    KEY_AGREEMENT(org.bouncycastle.asn1.x509.KeyUsage.keyAgreement),
    KEY_CERT_SIGN(org.bouncycastle.asn1.x509.KeyUsage.keyCertSign),
    CRL_SIGN(org.bouncycastle.asn1.x509.KeyUsage.cRLSign),
    ENCIPHER_ONLY(org.bouncycastle.asn1.x509.KeyUsage.encipherOnly),
    DECIPHER_ONLY(org.bouncycastle.asn1.x509.KeyUsage.decipherOnly);

    private final int keyUsage;

    KeyUsage(int keyUsage){
        this.keyUsage = keyUsage;
    }

    public int getKeyUsage(){
        return this.keyUsage;
    }
}
