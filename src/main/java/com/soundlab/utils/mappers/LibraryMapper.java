package com.soundlab.utils.mappers;

import com.soundlab.domain.Library;
import com.soundlab.dto.LibraryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LibraryMapper extends BaseMapper<Library, LibraryDTO> {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "playlistsNumber", target = "playlistsNumber")
    })
    LibraryDTO toDTO(Library library);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "playlistsNumber", target = "playlistsNumber")
    })
    Library toEntity(LibraryDTO libraryDTO);

}
