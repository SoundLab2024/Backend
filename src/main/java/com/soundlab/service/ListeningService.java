package com.soundlab.service;

import com.soundlab.domain.Listening;
import com.soundlab.domain.properties.TimeSlot;
import com.soundlab.dto.ListeningDTO;
import com.soundlab.dto.records.AddRemoveListeningDTO;
import com.soundlab.repository.ListeningRepository;
import com.soundlab.repository.SongRepository;
import com.soundlab.repository.UserRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.exceptions.StorageException;
import com.soundlab.utils.exceptions.UserNotFoundException;
import com.soundlab.utils.mappers.ListeningMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ListeningService implements BaseService<Listening, ListeningDTO, Long, Payload> {

    private final ListeningRepository listeningRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    private final ListeningMapper listeningMapper;


    public List<ListeningDTO> allSongListened(String prefix) {

        var s = this.songRepository.findByExactTitle(prefix);
        List<Listening> listenings = s.getListenings();

        return listenings.stream()
                .map(this.listeningMapper::toDTO)
                .toList();
    }


    public List<ListeningDTO> allUserListened(String mail){

        var u = this.userRepository.findById(mail).orElseThrow(()-> new UserNotFoundException("Utente non trovato."));
        List<Listening> listenings = u.getListenings();

        //Set<Long> seenSongIds = new HashSet<>();

        return listenings.stream()
                .map(this.listeningMapper::toDTO)
                .toList();
    }

    public List<ListeningDTO> recentListened(String mail){

        var u = this.userRepository.findById(mail).orElseThrow(()-> new UserNotFoundException("Utente non trovato."));
        List<Listening> listenings = u.getListenings();

        Set<Long> seenSongIds = new HashSet<>();

        return listenings.stream()
                .filter(listening -> seenSongIds.add(listening.getSong().getId()))
                .limit(3)
                .map(this.listeningMapper::toDTO)
                .toList();
    }

    public Payload insertListening(AddRemoveListeningDTO dto){

        var u = userRepository.findById(dto.userId()).orElseThrow(()-> new UserNotFoundException("Utente non trovato"));
        var s = songRepository.findById(dto.songId()).orElseThrow(()-> new StorageException("Canzone non trovata"));

        LocalDateTime date = LocalDateTime.now();
        TimeSlot fasciaOr;

        fasciaOr = switch (date.getHour()) {
            case 0, 1, 2, 3, 4, 5, 6 -> TimeSlot.Night;
            case 7, 8, 9, 10, 11, 12, 13 -> TimeSlot.Morning;
            case 14, 15, 16, 17, 18 -> TimeSlot.Afternoon;
            default -> TimeSlot.Evening;
        };

        var l = this.listeningRepository.save(
                Listening
                        .builder()
                        .user(u)
                        .song(s)
                        .data(date)
                        .timeSlot(fasciaOr)
                        .build()
        );


        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Canzone ascoltata con successo")
                .build();
    }

    @Override
    public ListeningDTO getSingle(Long id) {
        return null;
    }

    @Override
    public List<ListeningDTO> getAll() {
        return null;
    }

    @Override
    public Payload insert(Listening listening) {
        return null;
    }

    @Override
    public Payload update(Listening listening) {
        return null;
    }

    @Override
    public Payload delete(Long id) {
        return null;
    }

}
