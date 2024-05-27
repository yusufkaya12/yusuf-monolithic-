package com.threepounds.caseproject.service;

import com.threepounds.caseproject.data.entity.ValidationCode;
import com.threepounds.caseproject.data.repository.ValidationCodeRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValidationCodeService {

  private final ValidationCodeRepository validationCodeRepository;

  public ValidationCodeService(ValidationCodeRepository validationCodeRepository) {
    this.validationCodeRepository = validationCodeRepository;
  }

  public ValidationCode save(ValidationCode validationCode) {
    return validationCodeRepository.save(validationCode);
  }

  public Optional<ValidationCode> getCode(String otp) {
    return validationCodeRepository.findByOtp(otp);
  }

  public Optional<ValidationCode> getByCodeAndUserId(String otp, String userId) {
    return validationCodeRepository.findByOtpAndUserId(otp, userId);
  }

}