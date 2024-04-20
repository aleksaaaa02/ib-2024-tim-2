package com.bookify.pki.controller;

import com.bookify.pki.dto.CertificateDTO;
import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateAssociation;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;

    @GetMapping("/{certId}")
    public ResponseEntity<CertificateDTO> getCertificate(@PathVariable Long certId) {

        Certificate c=certificateService.getCertificateById(certId);

        CertificateDTO cDTO=new CertificateDTO(c);

        return new ResponseEntity<>(cDTO, HttpStatus.OK);

    }


    @PostMapping("/request")
    public ResponseEntity<String> createNewCertificateRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        CertificateRequest request = certificateService.createCertificateRequest(certificateRequestDTO);
        if(request.getId() == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("YEY", HttpStatus.OK);
    }

    @GetMapping("/{certId}/signed")
    public ResponseEntity<List<CertificateDTO>> getCertificatesSignedSubjects(@PathVariable Long certId) {

        List<Certificate> signedCertificates = certificateService.getSignedCertificates(certId);

        List<CertificateDTO> signedCertificateDTOs = new ArrayList<>();

        for (Certificate c : signedCertificates){
            signedCertificateDTOs.add(new CertificateDTO(c));
        }
        return new ResponseEntity<>(signedCertificateDTOs, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getAllCertificates() {

        return null;
    }

    @PostMapping("/{requestId}/{issuerId}/accept")
    public ResponseEntity<String> signCertificate(@PathVariable Long requestId, @PathVariable Long issuerId){
        certificateService.signCertificateRequest(issuerId, requestId);
        return new ResponseEntity<>("YAY",HttpStatus.OK);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<CertificateRequest> rejectCertificate(@PathVariable Long requestId){
        CertificateRequest request = certificateService.rejectCertificateRequest(requestId);
        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
