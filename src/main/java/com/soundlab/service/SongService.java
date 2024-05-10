package com.soundlab.service;

import com.soundlab.domain.Song;
import com.soundlab.dto.SongDTO;
import com.soundlab.repository.SongRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.mappers.SongMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService implements BaseService<Song, SongDTO, Long, Payload> {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    public List<SongDTO> searchSongs(String prefix){

        List<Song> songs = songRepository.findByTitleStartingWith(prefix);
        List<SongDTO> songDTOs = songs.stream()
                .map(song -> this.songMapper.toDTO(song))
                .collect(Collectors.toList());

        return songDTOs;
    }

    @Override
    public SongDTO getSingle(Long aLong) {
        return null;
    }

    @Override
    public List<SongDTO> getAll() {
        return null;
    }

    @Override
    public Payload insert(Song song) {
        return null;
    }

    @Override
    public Payload update(Song song) {
        return null;
    }

    @Override
    public Payload delete(Long aLong) {
        return null;
    }

}
