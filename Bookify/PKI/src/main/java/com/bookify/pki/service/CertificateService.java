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

    @Override
    public CertificateRequest createCertificateRequest(CertificateRequestDTO certificateRequestDTO) {
        CertificateRequest request = new CertificateRequest(null,
                certificateRequestDTO.getSubjectName(),
                certificateRequestDTO.getLocality(),
                certificateRequestDTO.getCountry(),
                certificateRequestDTO.getEmail(),
                certificateRequestDTO.getCertificateType(),
                CertificateRequestStatus.PENDING);
        return certificateRequestRepository.save(request);
    }

    @Override
    public void signCertificateRequest(Long issuerId, Long requestId) {
        Optional<CertificateRequest> requestOptional = certificateRequestRepository.findById(requestId);
        if(requestOptional.isEmpty()) return;
        CertificateRequest request = requestOptional.get();

        String alias = aliasMappingService.getCertificateAlias(issuerId);

        java.security.cert.Certificate c = keyStoreReader.readCertificate(keystoreLocation, keystorePassword, alias);


        try {
            PrivateKey privateKey = getPrivateKey(alias);
            JcaX509CertificateHolder holder = new JcaX509CertificateHolder((X509Certificate) c);
            Issuer issuer = new Issuer(privateKey, c.getPublicKey(), holder.getSubject());
            KeyPair keyPair = KeyUtils.generateKeyPair();
            if(keyPair == null) return;

            Subject subject = getSubject(request, keyPair);

            Date startDate = new Date();
            Date endDate = getEndDate(holder);
            CertificateBuilder certificateBuilder = new CertificateBuilder();
            certificateBuilder
                    .withIssuer(issuer)
                    .withPurpose(CertificatePurpose.END_ENTITY)
                    .withSubject(subject)
                    .withValidity(startDate, endDate);

            X509Certificate x509Certificate = certificateBuilder.build();
            if(x509Certificate == null) return;

            String subjectAlias=generateCertificateAlias(x509Certificate);
            saveCertificate(x509Certificate,subjectAlias);
            savePrivateKey(keyPair.getPrivate(),subjectAlias);

            Long subjectId = new Date().getTime();

            aliasMappingService.addSignedCertificate(issuerId,subjectId,subjectAlias);

            System.out.println(x509Certificate);
        }
        catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException | CertificateEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    private static Date getEndDate(JcaX509CertificateHolder holder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 2);
        Date endDate = calendar.getTime();

        if(!holder.isValidOn(endDate)){
            endDate= holder.getNotAfter();
        }
        return endDate;
    }

    private PrivateKey getPrivateKey(String alias) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        StringBuilder privateKeyContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(keyLocation+ alias +".key"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                privateKeyContent.append(line).append("\n");
            }
        }

        // Remove PEM header and footer
        String privateKeyPEM = privateKeyContent.toString()
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // Decode Base64-encoded data
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM);

        // Convert to PrivateKey object
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return keyFactory.generatePrivate(keySpec);
    }

    private static Subject getSubject(CertificateRequest request, KeyPair keyPair) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, request.getSubjectName());
        builder.addRDN(BCStyle.C, request.getCountry());
        builder.addRDN(BCStyle.L, request.getLocality());
        builder.addRDN(BCStyle.E, request.getEmail());
        return new Subject(keyPair.getPublic(), builder.build());
    }


    @Override
    public CertificateRequest rejectCertificateRequest(Long requestId) {
        Optional<CertificateRequest> requestOptional = certificateRequestRepository.findById(requestId);
        if(requestOptional.isEmpty()) return null;
        CertificateRequest request = requestOptional.get();
        request.setCertificateRequestStatus(CertificateRequestStatus.REJECTED);
        return certificateRequestRepository.save(request);
    }

//    private X509Certificate generateCertificate(Subject subject, Issuer issuer, Date startDate, Date endDate) {
//        try {
//
//            BigInteger randomNumber = BigInteger.valueOf(System.currentTimeMillis());
//            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
//            builder = builder.setProvider("BC");
//            ContentSigner contentSigner = builder.build(issuer.getPrivateKey());
//
//            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuer.getX500Name(),
//                    randomNumber,
//                    startDate,
//                    endDate,
//                    subject.getX500Name(),
//                    subject.getPublicKey());
//
//            X509CertificateHolder certHolder = certGen.build(contentSigner);
//            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
//            certConverter = certConverter.setProvider("BC");
//            return certConverter.getCertificate(certHolder);
//
//        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    void saveCertificate(X509Certificate cert,String alias){
        keyStoreWriter.loadKeyStore(keystoreLocation, keystorePassword.toCharArray());
        keyStoreWriter.write(alias,cert);
        keyStoreWriter.saveKeyStore(keystoreLocation, keystorePassword.toCharArray());
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
