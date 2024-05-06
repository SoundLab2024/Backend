package com.soundlab.utils.mappers;

import com.soundlab.domain.Song;
import com.soundlab.dto.SongDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SongMapper extends BaseMapper<Song, SongDTO>{
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "year", target = "year"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "genre", target = "genre")
    })
    SongDTO toDTO(Song song);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "year", target = "year"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "genre", target = "genre")
    })
    Song toEntity(SongDTO songDTO);

}
