package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * Model class that hides the sensitive data of a user
 */
@Data
public class UserDTO implements Serializable {

  /**
   * The email of the user
   */
  @JsonProperty("email")
  private String email;

  /**
   * The username of the user
   */
  @JsonProperty("username")
  private String username;
  /**
   * The role of the user
   */
  @JsonProperty("role")
  private String role;
}