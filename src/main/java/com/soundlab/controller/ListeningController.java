package com.soundlab.controller;

import com.soundlab.dto.ListeningDTO;
import com.soundlab.dto.records.AddRemoveListeningDTO;
import com.soundlab.service.ListeningService;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/listenings/", produces = {"application/json"})
public class ListeningController {

    private final ListeningService serviceLis;

    @GetMapping("recently/{id}")
    public List<ListeningDTO> recentListened(@PathVariable("id") String id){ return this.serviceLis.recentListened(id); }

    @GetMapping("all/{id}")
    public List<ListeningDTO> allUserListened(@PathVariable("id") String id) { return this.serviceLis.allUserListened(id); }

    @GetMapping("song/{prefix}")
    public List<ListeningDTO> allSongListened(@PathVariable("prefix") String prefix) { return this.serviceLis.allSongListened(prefix); }

    @PostMapping("new")
    public Payload insertListening(@RequestBody AddRemoveListeningDTO dto) { return this.serviceLis.insertListening(dto); }

}
