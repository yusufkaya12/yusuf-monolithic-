package com.threepounds.caseproject.controller;

import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.security.AuthenticationService;
import com.threepounds.caseproject.security.auth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {

        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @PostMapping("/passwordreset")
    public ResponseEntity<JwtAuthenticationResponse> passwordreset(@RequestBody PasswordResetRequest request) {
        return ResponseEntity.ok(authenticationService.passwordreset(request));
    }

    @PostMapping("confirm")
    public ResponseModel confirm(@RequestBody ConfirmRequest request) {
        return authenticationService.confirm(request);
    }


}
