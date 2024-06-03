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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
            return UserPayload
                    .builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .token("Utente con email già esistente")
                    .build();
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
                .statusCode(HttpStatus.OK.value())
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

    public Payload changePw(RegistrationDTO dto) {
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

    @Transactional
    public Payload changeUsername(RegistrationDTO dto) {
        /*
         * Sto utilizzando lo stesso DTO, per new e old password si intende new e old username
         */

        Optional<User> u = this.repository.findById(dto.email());
        if(u.isEmpty()){
            return Payload
                    .builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .msg("Utente richiesto non esiste")
                    .build();
        }

        if(dto.newPassword().equals(dto.oldPassword())){
            return Payload
                    .builder()
                    .statusCode(HttpStatus.CONFLICT.value())
                    .msg("Form non compilato correttamente")
                    .build();
        }

        User user = u.get();
        user.setUsername(dto.newPassword());
        this.repository.save(user);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Username modificato con successo")
                .build();
    }

    @Transactional
    public Payload changeEmail(RegistrationDTO dto) {
        Optional<User> optU = this.repository.findById(dto.email());
        if(optU.isEmpty()){
            return Payload
                    .builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .msg("Utente richiesto non esiste")
                    .build();
        }
        User u = optU.get();

        Optional<Library> optL = this.libraryRepo.findById(u.getLibrary().getId());
        if(optL.isEmpty()){
            return Payload
                    .builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .msg("Libreria richiesta non esiste")
                    .build();
        }
        Library l = optL.get();

        // Crea un nuovo utente con la nuova email
        var newUser = User
                .builder()
                .email(dto.newPassword())
                .password(u.getPassword())
                .username(u.getName())
                .role(u.getRole())
                .birth(u.getBirth())
                .gender(u.getGender())
                .active(true)
                .build();
        newUser = this.repository.save(newUser);

        l.setUser(newUser);
        u.setLibrary(null);

        this.repository.deleteById(u.getEmail());

        //newUser.setLibrary(l);
        //this.repository.save(newUser);

        return Payload
                .builder()
                .statusCode(HttpStatus.OK.value())
                .msg("Email modificata con successo")
                .build();
    }

}