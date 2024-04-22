package com.bookify.pki.repository;

import com.bookify.pki.model.UserCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserCertificateRepository extends JpaRepository<UserCertificate, Long> {
    UserCertificate findByUserId(Long userId);
}
