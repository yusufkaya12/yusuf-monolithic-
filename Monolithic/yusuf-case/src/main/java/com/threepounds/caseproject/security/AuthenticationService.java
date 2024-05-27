package com.threepounds.caseproject.security;


import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.security.auth.*;


public interface AuthenticationService {
  JwtAuthenticationResponse signup(SignUpRequest request);

  JwtAuthenticationResponse signin(SigninRequest request);
  
  JwtAuthenticationResponse passwordreset(PasswordResetRequest request);

  ResponseModel confirm(ConfirmRequest request);

}
