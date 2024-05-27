package com.threepounds.caseproject.security;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityUserService {
  UserDetailsService userDetailsService();
}
