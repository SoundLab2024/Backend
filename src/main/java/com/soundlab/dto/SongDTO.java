package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soundlab.domain.properties.SongType;
import lombok.Data;

@Data
public class SongDTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String title;

    @JsonProperty
    private Integer year;

    @JsonProperty
    private SongType type;

}
