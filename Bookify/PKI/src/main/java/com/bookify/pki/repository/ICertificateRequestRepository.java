package com.bookify.pki.repository;

import com.bookify.pki.model.CertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {

}
