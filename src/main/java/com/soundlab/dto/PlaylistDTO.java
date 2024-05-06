package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.domain.Song;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PlaylistDTO {

    @JsonProperty
    @JsonView(Views.LibraryPlaylists.class)
    private Long id;

    @JsonProperty
    @JsonView(Views.LibraryPlaylists.class)
    private String name;

    @JsonProperty
    @JsonView(Views.LibraryPlaylists.class)
    private Integer songsNumber;

    @JsonProperty
    @JsonView(Views.LibraryPlaylists.class)
    private String genre;

    @JsonProperty
    @JsonView(Views.LibraryPlaylists.class)
    private boolean favourite;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private List<SongDTO> songs;

}
