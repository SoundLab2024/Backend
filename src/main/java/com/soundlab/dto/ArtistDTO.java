package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.sql.Date;

@Data
public class ArtistDTO {

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private Long id;

    @JsonProperty
    @JsonView(Views.PlaylistSongs.class)
    private String name;

}
