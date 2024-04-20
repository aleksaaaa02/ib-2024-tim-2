package com.bookify.pki.controller;

import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;

    @GetMapping("/{certId}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable Long certId) {
        return null;
    }

    @PostMapping("/request")
    public ResponseEntity<String> createNewCertificateRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        CertificateRequest request = certificateService.createCertificateRequest(certificateRequestDTO);
        if(request.getId() == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("YEY", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getAllCertificates() {

        return null;
    }

    @PostMapping("/{issuerId}/{requestId}")
    public ResponseEntity<?> signCertificate(@PathVariable Long issuerId, @PathVariable Long requestId){
        certificateService.signCertificateRequest(-1L, requestId);
        return null;
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<CertificateRequest> rejectCertificate(@PathVariable Long requestId){
        CertificateRequest request = certificateService.rejectCertificateRequest(requestId);
        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
