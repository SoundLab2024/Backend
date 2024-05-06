package com.soundlab.service;

import com.soundlab.domain.Playlist;
import com.soundlab.domain.Song;
import com.soundlab.dto.PlaylistDTO;
import com.soundlab.dto.SongDTO;
import com.soundlab.repository.PlaylistRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.mappers.PlaylistMapper;
import com.soundlab.utils.mappers.SongMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService implements BaseService<Playlist, PlaylistDTO, Long, Payload> {
    private final PlaylistRepository repository;

    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;

    /**
     * Metodo che ritorna tutte le canzoni di una playlist
     */
    @Override
    public PlaylistDTO getSingle(Long id) {

        // Cerco la playlist tramite l'id fornito -> Cerco le canzoni associate tramite adds -> Mappo e ritorno la song list
        var p = this.repository.findById(id).orElseThrow(()-> new RuntimeException("Playlist non trovata"));
        List<Song> songs = p.getSongs();

        List<SongDTO> songDTOs = songs.stream()
                .map(song -> this.songMapper.toDTO(song))
                .collect(Collectors.toList());

        PlaylistDTO playlistDTO = this.playlistMapper.toDTO(p);
        playlistDTO.setSongs(songDTOs);

        return playlistDTO;
    }

    @Override
    public List<PlaylistDTO> getAll() {
        return null;
    }

    @Override
    public Payload insert(Playlist playlist) {
        return null;
    }

    @Override
    public Payload update(Playlist playlist) {
        return null;
    }

    @Override
    public Payload delete(Long aLong) {
        return null;
    }
}
