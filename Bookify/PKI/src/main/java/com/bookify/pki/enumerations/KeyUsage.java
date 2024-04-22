package com.bookify.pki.enumerations;

public enum KeyUsage {
    DIGITAL_SIGNATURE(org.bouncycastle.asn1.x509.KeyUsage.digitalSignature, 0),
    NON_REPUDIATION(org.bouncycastle.asn1.x509.KeyUsage.nonRepudiation, 1),
    KEY_ENCIPHERMENT(org.bouncycastle.asn1.x509.KeyUsage.keyEncipherment, 2),
    DATA_ENCIPHERMENT(org.bouncycastle.asn1.x509.KeyUsage.dataEncipherment, 3),
    KEY_AGREEMENT(org.bouncycastle.asn1.x509.KeyUsage.keyAgreement, 4),
    KEY_CERT_SIGN(org.bouncycastle.asn1.x509.KeyUsage.keyCertSign, 5),
    CRL_SIGN(org.bouncycastle.asn1.x509.KeyUsage.cRLSign, 6),
    ENCIPHER_ONLY(org.bouncycastle.asn1.x509.KeyUsage.encipherOnly, 7),
    DECIPHER_ONLY(org.bouncycastle.asn1.x509.KeyUsage.decipherOnly, 8);

    private final int keyUsage;
    private final int bitPosition;

    KeyUsage(int keyUsage, int bitPosition){
        this.keyUsage = keyUsage;
        this.bitPosition = bitPosition;
    }

    public int getKeyUsage(){
        return this.keyUsage;
    }

    public static KeyUsage getBitPosition(int bitPosition){
        for(KeyUsage usage : KeyUsage.values()){
            if(usage.bitPosition == bitPosition) return usage;
        }
        return null;
    }

}
