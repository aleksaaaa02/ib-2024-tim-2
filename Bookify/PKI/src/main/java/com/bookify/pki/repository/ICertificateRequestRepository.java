package com.bookify.pki.repository;

import com.bookify.pki.enumerations.CertificateRequestStatus;
import com.bookify.pki.model.CertificateRequest;
import org.bouncycastle.cert.cmp.CertificateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {
    List<CertificateRequest> findAllByCertificateRequestStatusIs(CertificateRequestStatus certificateRequest);
    Optional<CertificateRequest> findByUserId(Long userId);
}
