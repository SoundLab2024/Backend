package com.soundlab.config;

import com.soundlab.repository.UserRepository;
import com.soundlab.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for a variety of {@link Bean}.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  /**
   * Repository for the user-related operation
   */
  private final UserRepository repository;

  /**
   * Custom {@link UserDetailsService} implementation to make authentication challenging works as
   * expected. It uses the underlying repository
   *
   * @return UserDetailsService
   */
  @Bean
  UserDetailsService userDetailsService() {
    return email -> repository.findById(email)
        .orElseThrow(() -> new UserNotFoundException(email));
  }

  /**
   * Custom password encoder
   *
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  /**
   * Authentication Manager configuration
   *
   * @param cfg Config
   * @return {@link AuthenticationManager}
   * @throws Exception is returned if some errors may occur
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg)
      throws Exception {
    return cfg.getAuthenticationManager();
  }

  /**
   * Custom Authentication provider that uses the custom {@link #userDetailsService()} and
   * {@link #passwordEncoder()} implementation
   *
   * @return {@link AuthenticationProvider}
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

}
