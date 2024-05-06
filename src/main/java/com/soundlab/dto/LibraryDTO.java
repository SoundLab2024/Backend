package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.soundlab.domain.Playlist;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
@Data
public class LibraryDTO implements Serializable {

    /**
     * ID of the library
     */
    @JsonView(Views.LibraryPlaylists.class)
    @JsonProperty("id")
    private Long id;

    /**
     * Number of playlists in the lib
     */
    @JsonView(Views.LibraryPlaylists.class)
    @JsonProperty("playlistsNumber")
    private Integer playlistsNumber;

    /**
     * List of playlists in the lib
     */
    @JsonView(Views.LibraryPlaylists.class)
    @JsonProperty("playlists")
    private List<PlaylistDTO> playlists;

}
