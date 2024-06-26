package com.bookify.pki.controller;

import com.bookify.pki.dto.CertificateDTO;
import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.dto.NewCertificateDTO;
import com.bookify.pki.enumerations.CertificateRequestStatus;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateAssociation;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.service.interfaces.ICertificateService;
import org.bouncycastle.cert.cmp.CertificateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;

    @GetMapping("/{certId}")
    public ResponseEntity<CertificateDTO> getCertificate(@PathVariable Long certId) {
        Certificate c = certificateService.getCertificateById(certId);
        CertificateDTO certificateDTO = new CertificateDTO(c);
        return new ResponseEntity<>(certificateDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/byuser/{userId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getCertificateByUserId(@PathVariable Long userId){
        Long certificateId = certificateService.getCertificateByUserId(userId);
        Certificate certificate = certificateService.getCertificateById(certificateId);
        try {
            return new ResponseEntity<>(certificate.getX509Certificate().getEncoded(), HttpStatus.OK);
        } catch (CertificateEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{certId}/signed")
    public ResponseEntity<List<CertificateDTO>> getCertificatesSignedSubjects(@PathVariable Long certId) {
        List<Certificate> signedCertificates = certificateService.getSignedCertificates(certId);
        List<CertificateDTO> signedCertificateDTOs = new ArrayList<>();
        for (Certificate c : signedCertificates) {
            signedCertificateDTOs.add(new CertificateDTO(c));
        }
        return new ResponseEntity<>(signedCertificateDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CertificateDTO> createNewCertificate(@RequestBody NewCertificateDTO newCertificateDTO){
        if(!certificateService.validateCertificateChain(newCertificateDTO.getIssuerId()))return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Certificate certificate = certificateService.createNewCertificate(newCertificateDTO);
        if(certificate == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new CertificateDTO(certificate), HttpStatus.OK);
    }

    @GetMapping("/validate/{certId}")
    public ResponseEntity<Boolean> validateCertificateChain(@PathVariable Long certId){
        if(certificateService.validateCertificateChain(certId)) return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @DeleteMapping("/{certId}")
    public ResponseEntity<Long> deleteCertificate(@PathVariable Long certId){
        System.out.println("Deleting certificate with id: " + certId);
        Long certificateId = certificateService.deleteCertificate(certId);
        if(certificateId == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(certificateId, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        // Mislim da ova metoda nece biti potrebna
        return null;
    }
}
