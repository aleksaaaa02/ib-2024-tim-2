package com.bookify.pki.controller;

import com.bookify.pki.dto.CertificateDTO;
import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import com.bookify.pki.service.CertificateRequestService;
import com.bookify.pki.service.interfaces.ICertificateRequestService;
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
    private ICertificateRequestService certificateRequestService;

    @GetMapping("/{requestId}")
    public ResponseEntity<CertificateRequestDTO> getCertificateRequest(@PathVariable Long requestId) {
        CertificateRequest cr = certificateRequestService.getRequestById(requestId);
        CertificateRequestDTO crDTO=new CertificateRequestDTO(cr);
        return new ResponseEntity<>(crDTO, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<CertificateRequest> createNewCertificateRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        CertificateRequest request = certificateRequestService.createCertificateRequest(certificateRequestDTO);
        if(request.getId() == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);

    }


    @GetMapping("/all")
    public ResponseEntity<List<CertificateRequestDTO>> getAllCertificateRequests() {
        List<CertificateRequest> requests = certificateRequestService.getPendingCertificateRequest();
        List<CertificateRequestDTO> requestDTOS = new ArrayList<>();
        for(CertificateRequest request : requests){
            requestDTOS.add(new CertificateRequestDTO(request));
        }
        return new ResponseEntity<>(requestDTOS, HttpStatus.OK);
    }

    @PostMapping("/accept/{requestId}/{issuerId}")
    public ResponseEntity<CertificateRequest> acceptCertificateRequest(@PathVariable Long requestId,@PathVariable Long issuerId){
        
        CertificateRequest request = certificateRequestService.acceptCertificateRequest(issuerId, requestId);
        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);

    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<CertificateRequest> rejectCertificateRequest(@PathVariable Long requestId){

        CertificateRequest request = certificateRequestService.rejectCertificateRequest(requestId);
        if(request == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(request, HttpStatus.OK);

    }
}
