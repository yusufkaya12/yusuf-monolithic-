package com.threepounds.caseproject.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
  String extractUserName(String token);

  String generateToken(String username);

  boolean isTokenValid(String token, UserDetails userDetails);
}
