package com.soundlab.controller;

import com.soundlab.dto.LibraryDTO;
import com.soundlab.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/libs/", produces = {"application/json"})
public class LibraryController {

    private final LibraryService service;

    /**
     *   Retrieve a user library
     */
    @GetMapping("{id}")
    public LibraryDTO getLibrary(@PathVariable("id") Long id){ return this.service.getSingle(id); }

}
