package com.soundlab.controller;

import com.soundlab.dto.records.CredentialsDTO;
import com.soundlab.dto.records.RegistrationDTO;
import com.soundlab.service.AuthenticationService;
import com.soundlab.utils.response.Payload;
import com.soundlab.utils.response.UserPayload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for authentication related operation
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/authentication/", produces = {"application/json"})
public class AuthenticationController {

  /**
   * Services used internally to handle requests
   */
  private final AuthenticationService service;

  /**
   * Register a new user
   *
   * @param dto Structure representing the base credentials needed for the user
   * @return Status of the operation in a form of payload
   */
  @PostMapping("register")
  public UserPayload register(@Valid @RequestBody CredentialsDTO dto) {
    return this.service.register(dto);
  }

  /**
   * Authenticate an already existing user
   *
   * @param dto Structure representing the user
   * @return Status of the operation in a form of payload
   */
  @PostMapping("authenticate")
  public UserPayload authenticate(@Valid @RequestBody CredentialsDTO dto) {
    return this.service.authenticate(dto);
  }

  /**
   * Account confirmation for  an already existing user
   *
   * @param dto Structure representing the user
   * @return Status of the operation in a form of payload
   */
  @PostMapping("changepw")
  public Payload changePw(@Valid @RequestBody RegistrationDTO dto) {
    return this.service.changePw(dto);
  }

  @PostMapping("changeusern")
  public Payload changeUsername(@Valid @RequestBody RegistrationDTO dto) { return this.service.changeUsername(dto); }

  @PostMapping("changemail")
  public Payload changeEmail(@Valid @RequestBody RegistrationDTO dto) { return this.service.changeEmail(dto); }

}
