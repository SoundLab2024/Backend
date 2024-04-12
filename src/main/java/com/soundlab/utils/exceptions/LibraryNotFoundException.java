package com.soundlab.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LibraryNotFoundException extends RuntimeException {


    public LibraryNotFoundException(String msg) {
        super("La libreria: " + msg + " non è stata trovata.");
    }
}
