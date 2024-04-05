package com.soundlab.service;

import com.soundlab.domain.User;
import com.soundlab.dto.UserDTO;
import com.soundlab.repository.UserRepository;
import com.soundlab.security.JWTService;
import com.soundlab.service.base.BaseService;
import com.soundlab.utils.exceptions.UserNotFoundException;
import com.soundlab.utils.mappers.UserMapper;
import com.soundlab.utils.response.Payload;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<User, UserDTO, String, Payload> {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final JWTService jwtService;

    public UserDTO getCurrentUserDetails(String token) {

        return mapper.toDTO(this.repository.findById(this.jwtService.extractUsername(token)).orElseThrow(() -> new UserNotFoundException("")));

    }

    @Override
    public UserDTO getSingle(String id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    @Override
    public List<UserDTO> getAll() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public Payload insert(User u) {
        if (this.repository.findById(u.getEmail()).isPresent()) {
            return Payload
                    .builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .msg("Utente con email " + u.getEmail() + " già esistente")
                    .build();
        }

        this.repository.save(u);
        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Utente inserito correttamente")
                .build();
    }

    @Override
    public Payload update(User u) {
        if (this.repository.findById(u.getEmail()).isPresent()) {
            return Payload
                    .builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .msg("Utente con email " + u.getEmail() + " già esistente")
                    .build();
        }

        this.repository.save(u);
        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Utente inserito correttamente")
                .build();
    }

    @Override
    public Payload delete(String id) {
        if (this.repository.findById(id).isEmpty()) {
            return Payload
                    .builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .msg("L'utente richiesto non esiste")
                    .build();
        }

        this.repository.deleteById(id);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Utente eliminato correttamente")
                .build();
    }
}