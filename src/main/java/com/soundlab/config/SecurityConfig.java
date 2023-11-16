package com.soundlab.config;

import com.soundlab.security.JWTAuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Security related configuration
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  /**
   * AuthenticationProvider (available through beans)
   */
  private final AuthenticationProvider provider;
  /**
   * Authentication filter using JWT
   */
  private final JWTAuthenticationFilter filter;

  /**
   * Public available routes
   */
  @Value("${application.public_routes}")
  private String[] publicRoutes;

  /**
   * Private routes (requires authentication with JWT)
   */
  @Value("${application.admin_routes}")
  private String[] adminRoutes;

  /**
   * Custom filter chain capable of being matched against an HttpServletRequest
   *
   * @param http Http object to configure for specific http requests
   * @return {@link SecurityFilterChain}
   * @throws Exception is thrown if some errors occur
   */
  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http
  ) throws Exception {
    return http
        /*
         + Disable Cross-Site Request Forgery
         + Disable basic form login & httpBasic as we are not going to use in a Web Browser

         The security context in a application is stored in a ServerSecurityContextRepository.
         Its WebSessionServerSecurityContextRepository implementation, which is used by default,
         stores the context in session. Configuring a NoOpServerSecurityContextRepository instead
         would make our application stateless
         (Taken from -> https://stackoverflow.com/questions/56056404/disable-websession-creation-when-using-spring-security-with-spring-webflux)
        */
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // You know, lambdas >>>
        .authorizeHttpRequests(
            authz -> {
              // Public requests
              authz.requestMatchers(publicRoutes)
                  .permitAll(); // Use "/**" to permit whole access to the API without permissions
              // Admin only requests
              authz.requestMatchers(adminRoutes).hasRole(("ADMIN"));
              // Authenticate any other requests
              authz.anyRequest().authenticated();
            }
        )
        .authenticationProvider(provider)
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * Cors configuration (useful on site)
   *
   * @return CorsConfigurationSource
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("http://localhost:8005"));
    configuration.setAllowedMethods(List.of("GET", "POST"));
    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
