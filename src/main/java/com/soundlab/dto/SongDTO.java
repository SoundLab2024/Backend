package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.domain.properties.SongType;
import lombok.Data;

import java.util.List;

@Data
public class SongDTO {

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private Long id;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private String title;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private Integer year;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private SongType type;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private String genre;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private List<ArtistDTO> artists;

}
