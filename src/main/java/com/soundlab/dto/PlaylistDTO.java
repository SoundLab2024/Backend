package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class PlaylistDTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private Integer songsNumber;

    @JsonProperty
    private String genre;

    @JsonProperty
    private boolean favourite;

}
