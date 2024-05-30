package com.soundlab.service;

import com.soundlab.domain.Listening;
import com.soundlab.domain.Song;
import com.soundlab.dto.SongDTO;
import com.soundlab.repository.ListeningRepository;
import com.soundlab.repository.SongRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.mappers.SongMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService implements BaseService<Song, SongDTO, Long, Payload> {

    private final SongRepository songRepository;
    private final ListeningRepository listeningRepository;

    private final SongMapper songMapper;

    public List<SongDTO> searchSongs(String prefix){

        List<Song> songs = songRepository.findByTitleStartingWith(prefix);

        return songs.stream()
                .limit(5)
                .map(this.songMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SongDTO> searchByGenre(String prefix){

        List<Song> songs = songRepository.findAllByGenre(prefix);

        return songs.stream()
                .limit(10)
                .map(this.songMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SongDTO> searchByLastListened(){ // Questa funzione è m***a, ma veramente però.

        List<Listening> lastFour = this.listeningRepository.findLastFour(PageRequest.of(0, 4));

        return lastFour.stream()
                .map(listening -> songRepository.findById(listening.getSong().getId()).orElse(null))
                .filter(Objects::nonNull)
                .map(this.songMapper::toDTO)
                .collect(Collectors.toList());
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
