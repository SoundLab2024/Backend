package com.soundlab.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.dto.LibraryDTO;
import com.soundlab.dto.Views;
import com.soundlab.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/libs/", produces = {"application/json"})
public class LibraryController {

    private final LibraryService service;

    /**
     *   Retrieve a user library
     */
    @GetMapping("{id}")
    @JsonView(Views.LibraryPlaylists.class)
    public LibraryDTO getLibrary(@PathVariable("id") Long id){ return this.service.getSingle(id); }

}
