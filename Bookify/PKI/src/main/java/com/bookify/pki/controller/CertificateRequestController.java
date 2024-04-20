package com.bookify.pki.controller;

import com.bookify.pki.dto.CertificateDTO;
import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.service.CertificateRequestService;
import com.bookify.pki.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/certificate/request")
public class CertificateRequestController {

    @Autowired
    private CertificateRequestService certificateRequestService;

    @GetMapping("/{requestId}")
    public ResponseEntity<CertificateRequestDTO> getCertificateRequest(@PathVariable Long certId) {

        CertificateRequest cr=certificateRequestService.getRequestById(certId);

        CertificateRequestDTO crDTO=new CertificateRequestDTO(cr);

        return new ResponseEntity<>(crDTO, HttpStatus.OK);

    }


    @PostMapping()
    public ResponseEntity<String> createNewCertificateRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        CertificateRequest request = certificateRequestService.createCertificateRequest(certificateRequestDTO);
        if(request.getId() == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("YEY", HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getAllCertificateRequests() {

        return null;
    }

    @PostMapping("/{requestId}/accept/")
    public ResponseEntity<CertificateRequest> signCertificate(@PathVariable Long requestId,@RequestParam(value="issuerId",required = true) Long issuerId){
        CertificateRequest request = certificateRequestService.acceptCertificateRequest(requestId, issuerId);

        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<CertificateRequest> rejectCertificate(@PathVariable Long requestId){
        CertificateRequest request = certificateRequestService.rejectCertificateRequest(requestId);
        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
