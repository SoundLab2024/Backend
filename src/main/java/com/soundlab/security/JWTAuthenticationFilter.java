package com.soundlab.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Filter base class for JWT authentication */
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  /**
   * Services used internally to handle JWT token generation and validation
   */
  private final JWTService service;
  /**
   * Services used internally to handle user authentication validation
   */
  private final UserDetailsService userDetailsService;

  /**
   * Handler for every request (public and private endpoint)
   *
   * @param request     The given request
   * @param response    The given response
   * @param filterChain The chain of execution
   * @throws ServletException is thrown is some errors occurs
   * @throws IOException      is thrown is some errors occurs
   */
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String token, email;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    // Extract the token without the initial "BEARER" string
    token = authHeader.substring(7);
    email = service.extractUsername(token);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (email != null && authentication == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

      if (service.isTokenValid(token, userDetails)) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
