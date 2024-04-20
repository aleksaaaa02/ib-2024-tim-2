package com.bookify.pki.service;

import com.bookify.pki.builder.CertificateBuilder;
import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.enumerations.CertificatePurpose;
import com.bookify.pki.enumerations.CertificateRequestStatus;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.model.Issuer;
import com.bookify.pki.model.Subject;
import com.bookify.pki.repository.ICertificateRequestRepository;
import com.bookify.pki.service.interfaces.ICertificateService;
import com.bookify.pki.utils.KeyStoreReader;
import com.bookify.pki.utils.KeyStoreWriter;
import com.bookify.pki.utils.KeyUtils;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@Service
public class CertificateService implements ICertificateService {

    @Autowired
    private ICertificateRequestRepository certificateRequestRepository;

    @Autowired
    private KeyStoreReader keyStoreReader;

    @Autowired
    private KeyStoreWriter keyStoreWriter;

    @Autowired
    private AliasMappingService aliasMappingService;

    @Value("${keystore.location}")
    private String keystoreLocation;

    @Value("${keystore.password}")
    private String keystorePassword;

    @Value("${keys.location}")
    private String keyLocation;


    public CertificateService(){
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public Certificate getCertificateById(Long id){


        String certificateAlias = aliasMappingService.getCertificateAlias(id);

        java.security.cert.Certificate c = keyStoreReader.readCertificate(keystoreLocation, keystorePassword, certificateAlias);

        return new Certificate(id, (X509Certificate) c);
    }

    @Override
    public List<Certificate> getSignedCertificates(Long issuerId){

        List<Long> signedCertificateIds = aliasMappingService.getSignedCertificateIds(issuerId);

        List<Certificate> certificates=new ArrayList<>();

        for (Long id :signedCertificateIds){

            String certificateAlias= aliasMappingService.getCertificateAlias(id);
            java.security.cert.Certificate c=keyStoreReader.readCertificate("/Users/borislavcelar/keystore.jks", "bookify", certificateAlias);
            certificates.add(new Certificate(id, (X509Certificate) c));

        }

        return certificates;

    }


    public static Date getEndDate(JcaX509CertificateHolder holder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 2);
        Date endDate = calendar.getTime();

        if(!holder.isValidOn(endDate)){
            endDate= holder.getNotAfter();
        }
        return endDate;
    }
    public static Subject getSubject(CertificateRequest request, KeyPair keyPair) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, request.getSubjectName());
        builder.addRDN(BCStyle.C, request.getCountry());
        builder.addRDN(BCStyle.L, request.getLocality());
        builder.addRDN(BCStyle.E, request.getEmail());
        return new Subject(keyPair.getPublic(), builder.build());
    }

    public X509Certificate generateCertificate(Subject subject, Issuer issuer, Date startDate, Date endDate) {
        try {

            BigInteger randomNumber = BigInteger.valueOf(System.currentTimeMillis());
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");
            ContentSigner contentSigner = builder.build(issuer.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuer.getX500Name(),
                    randomNumber,
                    startDate,
                    endDate,
                    subject.getX500Name(),
                    subject.getPublicKey());

            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            return certConverter.getCertificate(certHolder);

        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }


    void saveCertificate(X509Certificate cert,Long issuerId,String alias){

        keyStoreWriter.loadKeyStore(keystoreLocation,keystorePassword.toCharArray());
        keyStoreWriter.write(alias,cert);
        keyStoreWriter.saveKeyStore(keystoreLocation,keystorePassword.toCharArray());

        Long subjectId=new Date().getTime();
        aliasMappingService.addSignedCertificate(issuerId,subjectId,alias);
    }

    void savePrivateKey(PrivateKey privateKey,String alias){

        // Convert the key to Base64 encoding
        byte[] keyBytes = privateKey.getEncoded();
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        // Add PEM headers and footers
        StringBuilder pemKey = new StringBuilder();
        pemKey.append("-----BEGIN PRIVATE KEY-----\n");
        pemKey.append(base64Key).append("\n");
        pemKey.append("-----END PRIVATE KEY-----\n");

        // Write the PEM encoded private key to the file
        try (Writer writer = new FileWriter(keyLocation + alias + ".key")) {
            writer.write(pemKey.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String generateCertificateAlias(X509Certificate cert){

        return cert.getIssuerX500Principal().getName().hashCode()+cert.getSerialNumber().toString();

    }

}
