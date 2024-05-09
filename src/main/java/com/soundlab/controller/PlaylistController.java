package com.soundlab.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.dto.PlaylistDTO;
import com.soundlab.dto.records.InsertPlaylistDTO;
import com.soundlab.dto.Views;
import com.soundlab.service.PlaylistService;
import com.soundlab.utils.response.Payload;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("createPl")
    public Payload insert(@Valid @RequestBody InsertPlaylistDTO dto){ return this.service.insertPlaylist(dto); }

    @DeleteMapping("{id}")
    public Payload delete(@PathVariable("id") Long id) {return this.service.delete(id); }

    @PostMapping("renamePl")
    public Payload renamePl(@RequestBody InsertPlaylistDTO dto){ return this.service.renamePl(dto); }

}
