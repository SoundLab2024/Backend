package com.soundlab.dto.records;

import lombok.Builder;

@Builder
public record InsertPlaylistDTO(String name, String genre, Long libId) { }
