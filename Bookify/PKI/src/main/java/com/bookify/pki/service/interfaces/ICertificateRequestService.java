package com.bookify.pki.service.interfaces;

import com.bookify.pki.dto.CertificateRequestDTO;
import com.bookify.pki.dto.NewCertificateRequestDTO;
import com.bookify.pki.enumerations.CertificateRequestStatus;
import com.bookify.pki.model.Certificate;
import com.bookify.pki.model.CertificateRequest;
import org.bouncycastle.cert.cmp.CertificateStatus;

import java.util.List;

public interface ICertificateRequestService {
    CertificateRequest getRequestById(Long id);
    List<Certificate> getSignedCertificates(Long issuerId);
    CertificateRequest createCertificateRequest(NewCertificateRequestDTO certificateRequestDTO);
    CertificateRequest acceptCertificateRequest(Long issuerId, Long requestId);
    CertificateRequest rejectCertificateRequest(Long requestId);
    void signCertificateRequest(CertificateRequest request,Long issuerId);
    List<CertificateRequest> getPendingCertificateRequest();
    CertificateRequestStatus getCertificateStatusRequest(Long userId);

}
