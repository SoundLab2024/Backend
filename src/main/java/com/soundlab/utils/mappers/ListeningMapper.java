package com.soundlab.utils.mappers;

import com.soundlab.domain.Listening;
import com.soundlab.dto.ListeningDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListeningMapper extends BaseMapper<Listening, ListeningDTO>{
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "data", target = "data"),
            @Mapping(source = "timeSlot", target = "timeSlot"),
            @Mapping(source = "user.username", target = "user"),
            //@Mapping(source = "song.title", target = "song")
            @Mapping(source = "song", target = "song")
    })
    @Override
    ListeningDTO toDTO(Listening listening);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "data", target = "data"),
            @Mapping(source = "timeSlot", target = "timeSlot"),
            @Mapping(target = "user", ignore = true),
            //@Mapping(target = "song", ignore = true)
            @Mapping(source = "song", target = "song")
    })
    @Override
    Listening toEntity(ListeningDTO listeningDTO);

}
