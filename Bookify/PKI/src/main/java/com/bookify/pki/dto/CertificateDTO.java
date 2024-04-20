package com.bookify.pki.dto;

import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.Issuer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    Long id;
    String issuer;
    String subject;
    Date dateFrom;
    Date dateTo;

    public CertificateDTO(Certificate certificate){

        this.id=certificate.getId();

        this.issuer=certificate.getX509Certificate().getIssuerX500Principal().getName();
        this.subject=certificate.getX509Certificate().getSubjectX500Principal().getName();
        this.dateTo=certificate.getX509Certificate().getNotAfter();
        this.dateFrom=certificate.getX509Certificate().getNotBefore();

    }

}
