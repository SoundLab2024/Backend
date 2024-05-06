package com.soundlab.utils.mappers;

import com.soundlab.domain.Artist;
import com.soundlab.dto.ArtistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ArtistMapper extends BaseMapper<Artist, ArtistDTO>{

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    ArtistDTO toDTO(Artist artist);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name")
    })
    Artist toEntity(ArtistDTO artistDTO);

}
