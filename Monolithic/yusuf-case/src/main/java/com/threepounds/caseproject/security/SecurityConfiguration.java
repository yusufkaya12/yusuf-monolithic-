package com.threepounds.caseproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final SecurityUserService userService;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      HandlerMappingIntrospector introspector) throws Exception {

    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

    http.csrf(csrfConfigurer ->
        csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/api/v1/**"),
            PathRequest.toH2Console()));

    http.headers(headersConfigurer ->
        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));


    http.authorizeHttpRequests(auth ->
        auth
            .requestMatchers(
                mvcMatcherBuilder.pattern("/v3/api-docs/**"),
                mvcMatcherBuilder.pattern("/v3/api-docs.yaml"),
                mvcMatcherBuilder.pattern("/swagger-ui/**"),
                mvcMatcherBuilder.pattern("/swagger-ui.html"),
                mvcMatcherBuilder.pattern("/v2/api-docs/**"),
                mvcMatcherBuilder.pattern("/swagger-resources/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern("/api/v1/auth/**")).permitAll()
            .requestMatchers(mvcMatcherBuilder.pattern("/api/v1/roles/**")).permitAll()

            //This line is optional in .authenticated() case as .anyRequest().authenticated()
            //would be applied for H2 path anyway
//            .requestMatchers(PathRequest.toH2Console()).permitAll()
            .anyRequest().authenticated()

    ).authenticationProvider(authenticationProvider()).addFilterBefore(
        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);;


    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userService.userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}
