package com.soundlab.service;

import com.soundlab.domain.Library;
import com.soundlab.domain.User;
import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import com.soundlab.dto.records.CredentialsDTO;
import com.soundlab.dto.records.RegistrationDTO;
import com.soundlab.repository.LibraryRepository;
import com.soundlab.repository.UserRepository;
import com.soundlab.security.JWTService;
import com.soundlab.utils.exceptions.UserNotFoundException;
import com.soundlab.utils.response.Payload;
import com.soundlab.utils.response.UserPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final LibraryRepository libraryRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JWTService jwtService;

    public UserPayload register(CredentialsDTO dto) {
        if (this.repository.findById(dto.email()).isPresent()) {

        }

        var s = User
                .builder()
                .email(dto.email())
                .password(encoder.encode(dto.password()))
                .username(dto.username())
                .role(Role.USER)
                .birth(dto.birth())
                .gender(Gender.getGender(dto.gender()))
                .active(true)
                .build();
        s = this.repository.save(s);

        var l = this.libraryRepo.save(
                Library
                        .builder()
                        .user(s)
                        .playlistsNumber(0)
                        .build()
        );

        s.setLibrary(l);
        this.repository.save(s);

        String token = jwtService.generateToken(s);
        return UserPayload.builder()
                .token(token)
                .libraryId(s.getLibrary().getId())
                .username(s.getName())
                .email(s.getEmail())
                .role(s.getRole().toString())
                .build();
    }


    public UserPayload authenticate(CredentialsDTO dto) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        User u = this.repository.findById(dto.email())
                .orElseThrow(() -> new UserNotFoundException(dto.email()));

        String token = jwtService.generateToken(u);
        return UserPayload
                .builder()
                .token(token)
                .libraryId(u.getLibrary().getId())
                .username(u.getName())
                .email(u.getEmail())
                .role(u.getRole().toString())
                .build();
    }

    public Payload confirm(RegistrationDTO dto) {
        if (this.repository.findById(dto.email()).isEmpty()) {
            return Payload
                    .builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .msg("Impossibile completare la richiesta. I dati specificati non sono presenti nel sistema")
                    .build();
        }

        User u = this.repository.findById(dto.email())
                .orElseThrow(() -> new UserNotFoundException(dto.email()));

        // Check if the specified password matches from the saved encrypted one
        if (
                encoder.matches(dto.oldPassword(), u.getPassword())
                        && !dto.oldPassword().equals(dto.newPassword())
        ) {
            u.setPassword(encoder.encode(dto.newPassword()));
            u.setActive(true);
            this.repository.save(u);
            return Payload
                    .builder()
                    .statusCode(HttpStatus.OK.value())
                    .msg("Operazione completata con successo")
                    .build();
        }

        return Payload
                .builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .msg("Impossibile completare la richiesta. I dati specificati non sono validi")
                .build();
    }
}