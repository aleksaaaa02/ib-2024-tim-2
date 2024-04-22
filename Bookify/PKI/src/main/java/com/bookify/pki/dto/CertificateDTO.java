package com.bookify.pki.dto;

import com.bookify.pki.enumerations.ExtendedKeyUsage;
import com.bookify.pki.enumerations.ExtensionsType;
import com.bookify.pki.enumerations.KeyUsage;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.Extension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.x500.X500Principal;
import java.security.cert.CertificateParsingException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDTO {
    private Long id;
    private String issuer;
    private String subject;
    private String dateFrom;
    private String dateTo;
    private String publicKey;
    private String serialNumber;
    private List<Extension> extensions;
    public CertificateDTO(Certificate certificate){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = certificate.getId();
        X500Principal issuer = certificate.getX509Certificate().getIssuerX500Principal();
        X500Principal subject = certificate.getX509Certificate().getSubjectX500Principal();
        Map<String,String> issuerNames = getName(issuer.getName("RFC2253"));
        Map<String, String> subjectNames = getName(subject.getName("RFC2253"));
        this.subject = subjectNames.get("CN");
        this.issuer = issuerNames.get("CN");
        byte[] key = certificate.getX509Certificate().getPublicKey().getEncoded();
        this.publicKey = Base64.getEncoder().encodeToString(key);
        this.serialNumber = certificate.getX509Certificate().getSerialNumber().toString();

        this.dateTo = certificate
                .getX509Certificate()
                .getNotAfter().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(format);
        this.dateFrom = certificate
                .getX509Certificate()
                .getNotBefore().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(format);

        try {
            extensions = new ArrayList<>();
            getCA(certificate);
            getExtendedKeyUsages(certificate);
            getKeyUsage(certificate);
            getSubjectAltrenativeNames(certificate);
        } catch (CertificateParsingException e) {
            throw new RuntimeException(e);
        }

    }

    private void getExtendedKeyUsages(Certificate certificate) throws CertificateParsingException {
        List<ExtendedKeyUsage> keyUsages = new ArrayList<>();
        List<String> extendedKeyUsages = certificate.getX509Certificate().getExtendedKeyUsage();
        if(extendedKeyUsages == null) return;
        for(String oid : extendedKeyUsages){
            keyUsages.add(ExtendedKeyUsage.getExtendedKeyUsage(oid));
        }
        if(!keyUsages.isEmpty()) extensions.add(new Extension(ExtensionsType.EXTENDED_KEY_USAGE, keyUsages));
    }

    private void getCA(Certificate certificate) {
        String isCa = certificate.getX509Certificate().getBasicConstraints() != -1 ? "true" : "false";
        extensions.add(new Extension(ExtensionsType.BASIC_CONSTRAINTS, isCa));
    }

    private void getKeyUsage(Certificate certificate) {
        List<KeyUsage> keyUsage = new ArrayList<>();
        boolean[] keyUsageFlags = certificate.getX509Certificate().getKeyUsage();
        if(keyUsageFlags == null) return;
        for(int i = 0; i < keyUsageFlags.length; i++){
            if(keyUsageFlags[i]) keyUsage.add(KeyUsage.getBitPosition(i));
        }
        if (!keyUsage.isEmpty()) extensions.add(new Extension(ExtensionsType.KEY_USAGE, keyUsage));
    }

    private void getSubjectAltrenativeNames(Certificate certificate) {
        Collection<List<?>> sanEntries = null;
        try {
            sanEntries = certificate.getX509Certificate().getSubjectAlternativeNames();
        } catch (CertificateParsingException e) {
           return;
        }
        if(sanEntries != null){
            for(List<?> sanEntry : sanEntries){
                int sanType = (Integer) sanEntry.get(0);
                Object sanValue = sanEntry.get(1);
                extensions.add(new Extension(ExtensionsType.SUBJECT_ALTERNATIVE_NAME, sanValue));
            }
        }
    }
    private static Map<String, String> getName(String fullName){
        Map<String, String> names = new HashMap<>();
        String[] tokens = fullName.split(",");
        for(String token : tokens){
            String[] entry = token.split("=");
            names.put(entry[0], entry[1]);
        }
       return names;
    }
}
