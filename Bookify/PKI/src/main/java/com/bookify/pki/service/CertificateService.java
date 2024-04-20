package com.bookify.pki.service;

import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.enumerations.CertificateRequestStatus;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.model.Issuer;
import com.bookify.pki.model.Subject;
import com.bookify.pki.repository.ICertificateRequestRepository;
import com.bookify.pki.service.interfaces.ICertificateService;
import com.bookify.pki.utils.KeyStoreReader;
import com.bookify.pki.utils.KeyUtils;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class CertificateService implements ICertificateService {

    @Autowired
    private ICertificateRequestRepository certificateRequestRepository;

    @Autowired
    private KeyStoreReader keyStoreReader;

    public CertificateService(){
        Security.addProvider(new BouncyCastleProvider());
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

        if(issuerId == -1){

            java.security.cert.Certificate c = keyStoreReader.readCertificate("C:/Fakultet/Informaciona Bezbednost/ib-2021-tim-2/bookify/PKI/src/main/resources/static/keystore.jks", "password123", "rootCA");
            try (
                   FileReader keyReader = new FileReader("C:/Fakultet/Informaciona Bezbednost/ib-2021-tim-2/bookify/PKI/src/main/resources/static/rootCAKey.key")){

                // Read private key
                PemReader pemReader = new PemReader(keyReader);
                PemObject pemObject = pemReader.readPemObject();
                byte[] privateKeyBytes = pemObject.getContent();
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

                JcaX509CertificateHolder holder = new JcaX509CertificateHolder((X509Certificate) c);


                Issuer issuer = new Issuer(privateKey, c.getPublicKey(), holder.getSubject());
                KeyPair keyPair = KeyUtils.generateKeyPair();
                X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
                builder.addRDN(BCStyle.CN, request.getSubjectName());
                builder.addRDN(BCStyle.C, request.getCountry());
                builder.addRDN(BCStyle.L, request.getLocality());
                builder.addRDN(BCStyle.E, request.getEmail());
                //UID (USER ID) je ID korisnika

                Subject subject = new Subject(keyPair.getPublic(), builder.build());
                Date startDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.YEAR, 1);
                Date endDate = calendar.getTime();


                X509Certificate x509Certificate = generateCertificate(subject, issuer, startDate, endDate);
                System.out.println(x509Certificate);
            }
            catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException | CertificateEncodingException e) {
                throw new RuntimeException(e);
            }
            System.out.println(c);
        }
    }

    @Override
    public CertificateRequest rejectCertificateRequest(Long requestId) {
        Optional<CertificateRequest> requestOptional = certificateRequestRepository.findById(requestId);
        if(requestOptional.isEmpty()) return null;
        CertificateRequest request = requestOptional.get();
        request.setCertificateRequestStatus(CertificateRequestStatus.REJECTED);
        return certificateRequestRepository.save(request);
    }

    private X509Certificate generateCertificate(Subject subject, Issuer issuer, Date startDate, Date endDate) {
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
}
