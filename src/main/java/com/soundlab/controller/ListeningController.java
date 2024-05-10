package com.soundlab.controller;

import com.soundlab.dto.ListeningDTO;
import com.soundlab.service.ListeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/data/listenings/", produces = {"application/json"})
public class ListeningController {

    private final ListeningService serviceLis;

    @GetMapping("{id}")
    public List<ListeningDTO> recentListened(@PathVariable("id") String id){ return this.serviceLis.recentListened(id); }

}
