package com.soundlab.service;

import com.soundlab.domain.Listening;
import com.soundlab.dto.ListeningDTO;
import com.soundlab.repository.ListeningRepository;
import com.soundlab.repository.UserRepository;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.exceptions.UserNotFoundException;
import com.soundlab.utils.mappers.ListeningMapper;
import com.soundlab.utils.response.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListeningService implements BaseService<Listening, ListeningDTO, Long, Payload> {

    private final ListeningRepository listeningRepository;
    private final UserRepository userRepository;

    private final ListeningMapper listeningMapper;

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
