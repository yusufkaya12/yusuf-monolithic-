package com.threepounds.caseproject.security;

import com.threepounds.caseproject.controller.mapper.UserMapper;
import com.threepounds.caseproject.controller.response.ResponseModel;
import com.threepounds.caseproject.data.entity.Role;
import com.threepounds.caseproject.data.entity.User;
import com.threepounds.caseproject.data.entity.ValidationCode;
import com.threepounds.caseproject.data.repository.UserRepository;
import com.threepounds.caseproject.exceptions.EmailCheckException;
import com.threepounds.caseproject.exceptions.NotFoundException;
import com.threepounds.caseproject.messaging.model.Messages;
import com.threepounds.caseproject.messaging.producer.ConfirmProducer;
import com.threepounds.caseproject.messaging.producer.RegistrationMessageProducer;
import com.threepounds.caseproject.security.auth.*;
import com.threepounds.caseproject.service.RoleService;

import java.util.*;

import com.threepounds.caseproject.service.ValidationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final ValidationCodeService validationCodeService;

  private final UserMapper userMapper;
  private final RoleService roleService;
  private final ConfirmProducer confirmProducer;
  private final RegistrationMessageProducer registrationMessageProducer;

  @Override
  public JwtAuthenticationResponse signup(SignUpRequest request) {
    User user = userMapper.userDtoToEntity(request);
    user.setActive(false);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    Role userRole = roleService.getByName("ROLE_USER")
        .orElseThrow(() -> new NotFoundException("Role not found"));
    List<Role> roles = new ArrayList<>();
    roles.add(userRole);
    user.setRoles(roles);
    Optional<User> emailEntry = userRepository.findByEmail(user.getEmail());
    if (emailEntry.isPresent()) {
      throw new EmailCheckException("This email is already exist");
    }
    userRepository.save(user);

    Messages message = Messages.builder().id(user.getId())
            .name(user.getEmail())
                .content("User registered.").build();

    registrationMessageProducer.sendQueue(message);

    var jwt = jwtService.generateToken(user.getUsername());
    Random random = new Random();
    StringBuilder code = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      code.append(random.nextInt(10));
    }
    ValidationCode validationCode = new ValidationCode();
    validationCode.setUserId(user.getId());
    validationCode.setOtp(code.toString());
    validationCode.setUsed(false);
    validationCodeService.save(validationCode);
    return JwtAuthenticationResponse.builder().otp(code.toString()).token(jwt).build();
  }

  @Override
  public ResponseModel<String> confirm(ConfirmRequest request) {

    Optional<ValidationCode> validationCode = validationCodeService.getByCodeAndUserId(
        request.getOtp(), String.valueOf(request.getUserId()));
    ResponseModel<String> model = new ResponseModel<>();

    if (validationCode.isPresent()) {
      User user = userRepository.findById(request.getUserId())
          .orElseThrow(() -> new NotFoundException("User not found."));
      user.setActive(true);
      userRepository.save(user);
      model.setStatusCode(HttpStatus.OK.value());
      model.setBody("User activated.");
      validationCode.get().setUsed(true);
      validationCodeService.save(validationCode.get());
      Messages message = Messages.builder().id(user.getId())
              .name(user.getEmail())
              .content("User activated.").build();

      confirmProducer.sendQueue(message);
    } else {
      model.setStatusCode(HttpStatus.BAD_REQUEST.value());
      model.setBody("Code or user invalid.");
    }
    return model;
  }


  @Override
  public JwtAuthenticationResponse signin(SigninRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    String jwt = jwtService.generateToken(user.getEmail());
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }

  @Override
  public JwtAuthenticationResponse passwordreset(PasswordResetRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
    user.setPassword(passwordEncoder.encode(request.getNew_password()));
    String jwt = jwtService.generateToken(user.getEmail());
    return JwtAuthenticationResponse.builder().token(jwt).build();
  }


}
