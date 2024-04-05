package com.soundlab;

import com.soundlab.domain.User;
import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import com.soundlab.repository.UserRepository;

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

    private final PasswordEncoder encoder;

    @Async
    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        logger.info("Populating sample users");
        userRepository.saveAll(List.of(
                // Used for swagger ui
                User.builder().username("user").email("string").password(encoder.encode("string")).active(true).role(Role.ADMIN).gender(Gender.Male).build(),
                User.builder().username("aaa").email("aaa@mail.com").password(encoder.encode("aaa")).active(true).role(Role.USER).gender(Gender.Male).build()
        ));

        logger.info("Done initialization!");
    }
}