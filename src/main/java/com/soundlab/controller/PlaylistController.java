package com.soundlab.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.dto.PlaylistDTO;
import com.soundlab.dto.Views;
import com.soundlab.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/playlist/", produces = {"application/json"})
public class PlaylistController {

    private final PlaylistService service;

    /**
     *  Retrieve a playlist from his Id
     */
    @GetMapping("{id}")
    @JsonView(Views.PlaylistSongs.class)
    public PlaylistDTO getPlaylist(@PathVariable("id") Long id){ return this.service.getSingle(id); }


}
