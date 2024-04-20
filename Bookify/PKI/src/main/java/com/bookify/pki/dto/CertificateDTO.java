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
    X500Name issuer;
    X500Name subject;
    Date dateFrom;
    Date dateTo;

    public CertificateDTO(Certificate certificate){

        this.id=certificate.getId();

        try {

            JcaX509CertificateHolder holder=new JcaX509CertificateHolder(certificate.getX509Certificate());
            this.issuer=holder.getIssuer();
            this.subject=holder.getSubject();
            this.dateTo=holder.getNotAfter();
            this.dateFrom=holder.getNotBefore();

        } catch (CertificateEncodingException e) {
            throw new RuntimeException(e);
        }

    }

}
