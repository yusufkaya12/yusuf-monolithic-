package com.threepounds.caseproject.data.repository;

import com.threepounds.caseproject.data.entity.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ValidationCodeRepository extends JpaRepository<ValidationCode,String> {
    Optional<ValidationCode> findByOtp(String otp);
    Optional<ValidationCode> findByOtpAndUserId(String otp, String userId);
}
