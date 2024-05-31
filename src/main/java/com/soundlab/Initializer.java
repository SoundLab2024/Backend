package com.soundlab;

import com.soundlab.domain.*;
import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import com.soundlab.domain.properties.SongType;
import com.soundlab.domain.properties.TimeSlot;
import com.soundlab.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final ListeningRepository listeningRepository;

    private final PasswordEncoder encoder;

    @Async
    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        logger.info("Populating sample users and libraries");

        // Creo degli Utenti
        List<User> u = new ArrayList<>();
        u = userRepository.saveAll(List.of(
                // Used for swagger ui
                User.builder().username("user").email("string@string.com").password(encoder.encode("string")).active(true).role(Role.ADMIN).gender(Gender.Male).build(),
                User.builder().username("aaa").email("aaa@mail.com").password(encoder.encode("aaa")).active(true).role(Role.USER).gender(Gender.Male).build()
        ));

        //Creo delle Librerie per gli utenti
        List<Library> l = new ArrayList<>();
        l = libraryRepository.saveAll(List.of(
                Library.builder().user(u.get(0)).playlistsNumber(1).id(1L).build(),
                Library.builder().user(u.get(1)).playlistsNumber(0).id(2L).build()
        ));

        //Gli associo le Librerie create e salvo
        u.get(0).setLibrary(l.get(0));
        u.get(1).setLibrary(l.get(1));
        this.userRepository.save(u.get(0));
        this.userRepository.save(u.get(1));

        // Creo delle Playlist che associo alle librerie
        List<Playlist> p = new ArrayList<>();
        p = playlistRepository.saveAll(List.of(
                Playlist.builder().id(1L).name("playy").genre("boh").favourite(true).songsNumber(0).library(l.get(0)).build(),
                Playlist.builder().id(2L).name("sonounapl").genre("wow").favourite(false).songsNumber(0).library(l.get(0)).build(),
                Playlist.builder().id(3L).name("questa").genre("ok").favourite(false).songsNumber(0).library(l.get(1)).build(),
                Playlist.builder().id(4L).name("quella").genre("no").favourite(false).songsNumber(0).library(l.get(1)).build()
        ));

        // Creo degli artisti
        List<Artist> a = new ArrayList<>();
        a = artistRepository.saveAll(List.of(
                Artist.builder().id(1L).name("Alberto Selly").nationality("Napoli").build()
        ));
        List<Artist> ar = new ArrayList<>();
        ar = artistRepository.saveAll(List.of(
                Artist.builder().id(2L).name("Los del Río").nationality("Nazione").build()
        ));

        // Creo delle canzoni e gli associo almeno un Artista
        List<Song> s = new ArrayList<>();
        s = songRepository.saveAll(List.of(
                Song.builder().id(1L).title("O ball ro cavall").genre("Swag").type(SongType.ORIGINAL).year(2012).artistsNumber(1).artists(a).build(),
                Song.builder().id(2L).title("Macarena").genre("Pop").type(SongType.ORIGINAL).year(1993).artistsNumber(1).artists(ar).build()
        ));

        // Aggiungo le canzoni create prima nelle playlist
        p.get(0).setSongs(s);
        p.get(0).setSongsNumber(2);
        this.playlistRepository.save(p.get(0));

        p.get(2).setSongs(s);
        p.get(2).setSongsNumber(2);
        this.playlistRepository.save(p.get(2));

        // Aggiungo degli ascolti finti
        List<Listening> h = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime time1 = LocalDateTime.parse("05-05-2000 21:30:05", formatter);
        LocalDateTime time2 = LocalDateTime.parse("10-10-2010 09:30:05", formatter);
        h = listeningRepository.saveAll(List.of(
           Listening.builder().id(1L).data(time1).timeSlot(TimeSlot.Afternoon).user(u.get(0)).song(s.get(0)).build(),
           Listening.builder().id(2L).data(time2).timeSlot(TimeSlot.Morning).user(u.get(0)).song(s.get(0)).build(),
           Listening.builder().id(3L).data(time1).timeSlot(TimeSlot.Afternoon).user(u.get(0)).song(s.get(1)).build()
        ));


        logger.info("Done initialization!");
    }
}