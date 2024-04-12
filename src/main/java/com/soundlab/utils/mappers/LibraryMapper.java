package com.soundlab.utils.mappers;

import com.soundlab.domain.Library;
import com.soundlab.dto.LibraryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LibraryMapper extends BaseMapper<Library, LibraryDTO> {

    LibraryDTO toDTO(Library library);

    Library toEntity(LibraryDTO libraryDTO);

}
