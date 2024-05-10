package com.soundlab.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soundlab.domain.properties.TimeSlot;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ListeningDTO {

    @JsonProperty
    private Long id;

    @JsonProperty
    private LocalDateTime data;

    @JsonProperty
    private TimeSlot timeSlot;

    @JsonProperty
    private String user;

    /*@JsonProperty
    private String song;*/

    @JsonProperty
    private SongDTO song;

}
