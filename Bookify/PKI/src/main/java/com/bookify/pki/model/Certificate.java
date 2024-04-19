package com.bookify.pki.model;

import java.security.cert.X509Certificate;
import java.util.Date;

public class Certificate {
    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private Date startDate;
    private Date endDate;

    // svi prethodni podaci mogu da se izvuku i iz X509Certificate, osim privatnog kljuca issuera
    private X509Certificate x509Certificate;
}
