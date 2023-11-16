package com.soundlab;

import com.soundlab.domain.User;
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
        User.builder().email("string").password(encoder.encode("string")).build(),
        User.builder().email("email1@mail.com").password(encoder.encode("password")).build(),
        User.builder().email("email2@mail.com").password(encoder.encode("password")).build(),
        User.builder().email("email3@mail.com").password(encoder.encode("password")).build(),
        User.builder().email("email4@mail.com").password(encoder.encode("password")).build(),
        User.builder().email("email5@mail.com").password(encoder.encode("password")).build()
    ));

    logger.info("Done initialization!");
  }
}