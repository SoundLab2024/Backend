package com.soundlab.dto.records;

import lombok.Builder;

@Builder
public record AddRemoveSongDTO(Long idPlaylist, Long idSong) { }
