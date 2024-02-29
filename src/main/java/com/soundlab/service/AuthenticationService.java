package com.soundlab.service;

import com.soundlab.domain.User;
import com.soundlab.dto.CredentialsDTO;
import com.soundlab.dto.RegistrationDTO;
import com.soundlab.repository.UserRepository;
import com.soundlab.security.JWTService;
import com.soundlab.utils.exceptions.UserNotFoundException;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder encoder;
  private final AuthenticationManager manager;
  private final JWTService jwtService;

  public Payload register(CredentialsDTO dto) {
    if (this.repository.findById(dto.email()).isPresent()) {
      return Payload
          .builder()
          .statusCode(HttpStatus.CONFLICT.value())
          .msg(
              "Impossibile completare la richiesta. La email specificata è già presente nel sistema")
          .build();
    }

    this.repository.save(
        User
            .builder()
            .email(dto.email())
            // Save the password in an encrypted way
            .password(encoder.encode(dto.password()))
            // TODO: Add the other fields if needed
            .build()
    );
    return Payload.builder().statusCode(HttpStatus.OK.value())
        .msg("Operazione completata con successo").build();
  }


  public Payload authenticate(CredentialsDTO dto) {
    manager.authenticate(
        new UsernamePasswordAuthenticationToken(
            dto.email(),
            dto.password()
        )
    );

    User u = this.repository.findById(dto.email())
        .orElseThrow(() -> new UserNotFoundException(dto.email()));

    String token = jwtService.generateToken(u);
    return Payload
        .builder()
        .statusCode(HttpStatus.OK.value())
        .msg(token)
        .build();
  }

  public Payload confirm(RegistrationDTO dto) {
    if (this.repository.findById(dto.email()).isEmpty()) {
      return Payload
          .builder()
          .statusCode(HttpStatus.CONFLICT.value())
          .msg("Impossibile completare la richiesta. I dati specificati non sono presenti nel sistema")
          .build();
    }

    User u = this.repository.findById(dto.email())
        .orElseThrow(() -> new UserNotFoundException(dto.email()));

    // Check if the specified password matches from the saved encrypted one
    if (
        encoder.matches(dto.oldPassword(), u.getPassword())
            && !dto.oldPassword().equals(dto.newPassword())
    ) {
      u.setPassword(encoder.encode(dto.newPassword()));
      u.setActive(true);
      this.repository.save(u);
      return Payload
          .builder()
          .statusCode(HttpStatus.OK.value())
          .msg("Operazione completata con successo")
          .build();
    }

    return Payload
        .builder()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .msg("Impossibile completare la richiesta. I dati specificati non sono validi")
        .build();
  }
}