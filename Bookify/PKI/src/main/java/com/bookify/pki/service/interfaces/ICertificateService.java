package com.bookify.pki.service.interfaces;

import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICertificateService {
    List<Certificate> getSignedCertificates(Long issuerId);
    Certificate getCertificateById(Long id);
}
