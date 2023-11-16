package com.soundlab.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception handler representing the HTTP BAD Request response specific for files
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StorageException extends RuntimeException {

  public StorageException(String s) {
    super("Impossibile completare la richiesta (file: " + s + ")");
  }
}
