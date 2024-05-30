package com.soundlab.controller;

import com.soundlab.dto.SongDTO;
import com.soundlab.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/song/", produces = {"application/json"})
public class SongController {

    private final SongService service;

    @GetMapping("search/{prefix}")
    public List<SongDTO> searchSongs(@PathVariable("prefix") String prefix) { return this.service.searchSongs(prefix); }

    @GetMapping("search/genre/{prefix}")
    public List<SongDTO> searchByGenre(@PathVariable("prefix") String prefix) { return this.service.searchByGenre(prefix); }

}
