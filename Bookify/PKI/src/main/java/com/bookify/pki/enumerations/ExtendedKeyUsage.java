package com.bookify.pki.enumerations;

public enum ExtendedKeyUsage {
    SERVER_AUTHENTICATION("1.3.6.1.5.5.7.3.1"),
    CLIENT_AUTHENTICATION("1.3.6.1.5.5.7.3.2"),
    CODE_SIGNING("1.3.6.1.5.5.7.3.3"),
    EMAIL("1.3.6.1.5.5.7.3.4"),
    TIMESTAMPING("1.3.6.1.5.5.7.3.8"),
    OCSP_SIGNING("1.3.6.1.5.5.7.3.9");

    private final String value;

    ExtendedKeyUsage(final String value){
        this.value = value;
    }

    public String getOID(){
        return this.value;
    }

    public static ExtendedKeyUsage getExtendedKeyUsage(String oid){
        for(ExtendedKeyUsage extendedKeyUsage : ExtendedKeyUsage.values()){
            if(extendedKeyUsage.value.equalsIgnoreCase(oid)) return extendedKeyUsage;
        }
        return null;
    }
}
