package com.soundlab.controller;

import com.soundlab.domain.User;
import com.soundlab.dto.UserDTO;
import com.soundlab.service.UserService;
import com.soundlab.utils.response.Payload;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Rest Controller for user related operation */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/users/", produces = {"application/json"})
public class UserController {

  /**
   * Services used internally to handle requests
   */
  private final UserService service;

  @GetMapping("retrieve/{token}")
  public UserDTO getCurrentUserDetails(@PathVariable("token") String token) { return this.service.getCurrentUserDetails(token); }

  /**
   * Retrieve a single user from the database
   *
   * @param id Identifier of the user
   * @return Info about the user or an error if the specified id is not present
   */
  @GetMapping("{id}")
  public UserDTO getSingle(@Email @PathVariable("id") String id) {
    return this.service.getSingle(id);
  }

  /**
   * Retrieve all the users from the database
   *
   * @return List of {@link UserDTO}
   */
  @GetMapping
  public List<UserDTO> getAll() {
    return this.service.getAll();
  }


  /**
   * Insert a new user into the database
   *
   * @param user Structure of the user in a form of {@link User}
   * @return Status of the operation in a form of payload
   */
  @PostMapping
  public Payload insert(@Valid @RequestBody User user) {
    return this.service.insert(user);
  }

  /**
   * Update user infos into the database
   *
   * @param user Structure of the user in a form of {@link User}
   * @return Status of the operation in a form of payload
   */
  @PutMapping
  public Payload update(@Valid @RequestBody User user) {
    return this.service.update(user);
  }

  /**
   * Delete user infos from the database
   *
   * @param id Identifier of the user
   * @return Status of the operation in a form of payload
   */
  @DeleteMapping("{id}")
  public Payload delete(@Email @PathVariable("id") String id) {
    return this.service.delete(id);
  }
}
