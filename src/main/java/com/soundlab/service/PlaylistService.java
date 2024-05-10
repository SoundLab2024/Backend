package com.soundlab.service;

import com.soundlab.domain.Library;
import com.soundlab.domain.Playlist;
import com.soundlab.domain.Song;
import com.soundlab.dto.PlaylistDTO;
import com.soundlab.dto.records.InsertPlaylistDTO;
import com.soundlab.dto.SongDTO;
import com.soundlab.repository.LibraryRepository;
import com.soundlab.repository.PlaylistRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.exceptions.LibraryNotFoundException;
import com.soundlab.utils.exceptions.StorageException;
import com.soundlab.utils.mappers.PlaylistMapper;
import com.soundlab.utils.mappers.SongMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService implements BaseService<Playlist, PlaylistDTO, Long, Payload> {
    private final PlaylistRepository playlistRepo;
    private final LibraryRepository libraryRepo;

    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;

    /**
     * Metodo che ritorna tutte le canzoni di una playlist
     */
    @Override
    @Transactional
    public PlaylistDTO getSingle(Long id) {

        // Cerco la playlist tramite l'id fornito -> Cerco le canzoni associate tramite adds -> Mappo e ritorno la song list
        var p = this.playlistRepo.findById(id).orElseThrow(()-> new RuntimeException("Playlist non trovata"));
        List<Song> songs = p.getSongs();

        List<SongDTO> songDTOs = songs.stream()
                .map(song -> this.songMapper.toDTO(song))
                .collect(Collectors.toList());

        PlaylistDTO playlistDTO = this.playlistMapper.toDTO(p);
        playlistDTO.setSongs(songDTOs);

        return playlistDTO;
    }

    public Payload insertPlaylist(InsertPlaylistDTO pl) {

        // Cerco la libreria dell'utente nel db per aumentare di 1 il count delle playlist
        Library l = this.libraryRepo.findById(pl.libId()).orElseThrow(()-> new LibraryNotFoundException("Libreria non trovata"));
        int oldNumber = l.getPlaylistsNumber();
        l.setPlaylistsNumber(oldNumber + 1);
        //la salvo di nuovo
        this.libraryRepo.save(l);

        // Buildo la playlist e la salvo
        var p = Playlist
                .builder()
                .name(pl.name())
                .genre(pl.genre())
                .favourite(false)
                .library(l)
                .songsNumber(0)
                .build();
        p = this.playlistRepo.save(p);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg(String.valueOf(p.getId()))
                .build();
    }

    @Override
    public Payload delete(Long id) {
        var p = this.playlistRepo.findById(id);
        if (p.isEmpty()) {
            return Payload
                    .builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .msg("Playlist richiesta non esiste")
                    .build();
        }

        this.playlistRepo.deleteById(id);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Playlist eliminata correttamente")
                .build();
    }

    public Payload renamePl(InsertPlaylistDTO dto){
        // UTILIZZO LA STESSA VARIABILE PER COMODITA' MA SI INTENDE L'ID DELLA PLAYLIST DA RINOMINARE
        var p = this.playlistRepo.findById(dto.libId()).orElseThrow(()-> new StorageException("Playlist non trovata."));
        p.setName(dto.name());
        p.setGenre(dto.genre());
        this.playlistRepo.save(p);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Playlist modificata con successo")
                .build();
    }

    @Override
    public List<PlaylistDTO> getAll() {
        return null;
    }

    @Override
    public Payload update(Playlist playlist) {
        return null;
    }

    @Override
    public Payload insert(Playlist playlist) {
        return null;
    }

}
