package com.soundlab.utils.mappers;

import com.soundlab.domain.Playlist;
import com.soundlab.dto.PlaylistDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PlaylistMapper extends BaseMapper<Playlist, PlaylistDTO>{

    PlaylistDTO toDTO(Playlist playlist);

    Playlist toEntity(PlaylistDTO playlistDTO);

}
