package com.soundlab.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/** Service used to generate and validate JWT tokens */
@Service
public class JWTService {

  /**
   * The secret key used to sign tokens
   */
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  /**
   * The expiration of tokens
   */
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;

  /**
   * Utility method to extract the username info from a token
   *
   * @param token String that contains the username info
   * @return The username (if present)
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Generic method to extract claims for the token
   *
   * @param token          String containing all infos in a JWT format
   * @param claimsResolver function to handle the request
   * @param <T>            Generic Type
   * @return Generic type to generalize condition
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Utility method to generate the token from a {@link UserDetails}
   *
   * @param userDetails Provides core user information
   * @return a valid token
   */
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  /**
   * Utility method to generate the token from a Map of claims and {@link UserDetails}
   *
   * @param extraClaims Provided claims
   * @param userDetails Provides core user information
   * @return a valid token
   */
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }

  /**
   * Actual method that generated a valid token
   *
   * @param claims     Provided Claims
   * @param u          Core user infos
   * @param expiration Expiration date
   * @return Token
   */
  private String buildToken(
      Map<String, Object> claims,
      UserDetails u,
      long expiration
  ) {
    return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(u.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Helper method to check if a token is valid
   *
   * @param token       Token to check
   * @param userDetails Core user infos
   * @return Info about the operation
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  /**
   * Helper method to check if a token is expired
   *
   * @param token Token to check
   * @return Info about the operation
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Helper method to extract the expiration date of a token
   *
   * @param token Token to check
   * @return Info about the operation
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Helper method to extract the Claims from a token
   *
   * @param token Token to check
   * @return Claims (if present)
   */
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * @return Signin key generation
   */
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
