package com.bookify.pki.service.interfaces;

import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.CertificateRequest;
import org.springframework.stereotype.Service;

@Service
public interface ICertificateService {
    CertificateRequest createCertificateRequest(CertificateRequestDTO certificateRequestDTO);
    void signCertificateRequest(Long issuerId, Long requestId);
}
