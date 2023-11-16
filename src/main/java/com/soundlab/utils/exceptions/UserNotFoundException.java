package com.soundlab.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception handler representing the HTTP NotFound response specific for users
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String s) {
    super("Impossibile trovare l'utente richiesto (" + s + ")");
  }
}