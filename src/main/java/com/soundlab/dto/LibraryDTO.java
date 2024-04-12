package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soundlab.domain.Playlist;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
@Data
public class LibraryDTO implements Serializable {

    /**
     * ID of the library
     */
    @JsonProperty("id")
    private Long id;

    /**
     * Number of playlists in the lib
     */
    @JsonProperty("playlistsNumber")
    private Integer playlistsNumber;

    /**
     * List of playlists in the lib
     */
    @JsonProperty("playlists")
    private Set<PlaylistDTO> playlists;

}
