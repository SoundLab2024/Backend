package com.soundlab;

import com.soundlab.domain.Library;
import com.soundlab.domain.User;
import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import com.soundlab.repository.LibraryRepository;
import com.soundlab.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final Logger logger = LoggerFactory.getLogger(Initializer.class);

    private final UserRepository userRepository;
    private final LibraryRepository libraryRepository;

    private final PasswordEncoder encoder;

    @Async
    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        logger.info("Populating sample users and libraries");

        List<User> u = new ArrayList<>();
        u = userRepository.saveAll(List.of(
                // Used for swagger ui
                User.builder().username("user").email("string").password(encoder.encode("string")).active(true).role(Role.ADMIN).gender(Gender.Male).build(),
                User.builder().username("aaa").email("aaa@mail.com").password(encoder.encode("aaa")).active(true).role(Role.USER).gender(Gender.Male).build()
        ));

        List<Library> l = new ArrayList<>();
        l = libraryRepository.saveAll(List.of(
                Library.builder().user(u.get(0)).playlistsNumber(0).id(1L).build(),
                Library.builder().user(u.get(1)).playlistsNumber(0).id(2L).build()
        ));

        u.get(0).setLibrary(l.get(0));
        u.get(1).setLibrary(l.get(1));
        this.userRepository.save(u.get(0));
        this.userRepository.save(u.get(1));

        logger.info("Done initialization!");
    }
}