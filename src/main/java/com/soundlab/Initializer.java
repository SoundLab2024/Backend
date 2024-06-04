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
                Artist.builder().id(31L).name("Alberto Selly").nationality("Italy").build()
        ));
        List<Artist> ar = new ArrayList<>();
        ar = artistRepository.saveAll(List.of(
                Artist.builder().id(32L).name("Los del Río").nationality("Spain").build()
        ));
        // Creo delle canzoni e gli associo almeno un Artista
        List<Song> s = new ArrayList<>();
        s = songRepository.saveAll(List.of(
                Song.builder().id(1L).title("O ball ro cavall").genre("Neomelodico").type(SongType.ORIGINAL).year(2012).artistsNumber(1).artists(a).build(),
                Song.builder().id(2L).title("Macarena").genre("Pop").type(SongType.ORIGINAL).year(1993).artistsNumber(1).artists(ar).build()
        ));

        // Popolo gli artisti nel DB
        List<Artist> arr = new ArrayList<>();
        arr = artistRepository.saveAll(List.of(
                Artist.builder().id(1L).name("Adele").nationality("British").build(),
                Artist.builder().id(2L).name("Joey Rory").nationality("American").build(),
                Artist.builder().id(3L).name("Draaco Aventura").nationality("British").build(),
                Artist.builder().id(4L).name("Justin Bieber").nationality("Canadian").build(),
                Artist.builder().id(5L).name("Peer van Mladen").nationality("Dutch").build(),
                Artist.builder().id(6L).name("Chris Janson").nationality("American").build(),
                Artist.builder().id(7L).name("One Direction").nationality("British").build(),
                Artist.builder().id(8L).name("Drake").nationality("Canadian").build(),
                Artist.builder().id(9L).name("Carrie Underwood").nationality("American").build(),
                Artist.builder().id(10L).name("Ed Sheeran").nationality("British").build(),
                Artist.builder().id(11L).name("Taylor Swift").nationality("Pop").build(),
                Artist.builder().id(12L).name("SayWeCanFly").nationality("Rock").build(),
                Artist.builder().id(13L).name("Selena Gomez").nationality("Pop").build(),
                Artist.builder().id(14L).name("Chris Brown").nationality("Pop").build(),
                Artist.builder().id(15L).name("Nicki Minaj").nationality("Hip-Hop").build(),
                Artist.builder().id(16L).name("Fifth Harmony").nationality("Pop").build(),
                Artist.builder().id(17L).name("Thomas Rhett").nationality("Country").build(),
                Artist.builder().id(18L).name("Eminem").nationality("Rap").build(),
                Artist.builder().id(19L).name("Beyoncé").nationality("Pop").build(),
                Artist.builder().id(20L).name("Meghan Trainor").nationality("American").build(),
                Artist.builder().id(21L).name("Twenty One Pilots").nationality("Alternative").build(),
                Artist.builder().id(22L).name("Fetty Wap").nationality("Hip-Hop").build(),
                Artist.builder().id(23L).name("5 Seconds Of Summer").nationality("Rock").build(),
                Artist.builder().id(24L).name("Jason Aldean").nationality("Country").build(),
                Artist.builder().id(25L).name("Luke Bryan").nationality("Country").build(),
                Artist.builder().id(26L).name("Blake Shelton").nationality("Country").build(),
                Artist.builder().id(27L).name("Rihanna").nationality("Pop").build(),
                Artist.builder().id(28L).name("Elvis Presley").nationality("Rock").build(),
                Artist.builder().id(29L).name("Ariana Grande").nationality("Pop").build(),
                Artist.builder().id(30L).name("JD Shelburne").nationality("Country").build()
        ));

        List<Song> so = new ArrayList<>();
        s = songRepository.saveAll(List.of(
                Song.builder().id(1L).title("Hello").genre("Pop").type(SongType.ORIGINAL).year(2015).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(2L).title("Rolling in the Deep").genre("Pop").type(SongType.ORIGINAL).year(2010).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(3L).title("Someone Like You").genre("Pop").type(SongType.ORIGINAL).year(2011).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(4L).title("Set Fire to the Rain").genre("Pop").type(SongType.ORIGINAL).year(2011).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(5L).title("Skyfall").genre("Film Score").type(SongType.ORIGINAL).year(2012).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(6L).title("Easy on Me").genre("Pop").type(SongType.ORIGINAL).year(2021).artistsNumber(1).artists(arr.subList(0, 1)).build(),
                Song.builder().id(7L).title("When I'm Gone").genre("Country").type(SongType.ORIGINAL).year(2012).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(8L).title("Cheater, Cheater").genre("Country").type(SongType.ORIGINAL).year(2008).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(9L).title("That's Important to Me").genre("Country").type(SongType.ORIGINAL).year(2010).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(10L).title("Josephine").genre("Country").type(SongType.ORIGINAL).year(2012).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(11L).title("This Song's for You").genre("Country").type(SongType.ORIGINAL).year(2010).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(12L).title("I'll Fly Away").genre("Gospel").type(SongType.COVER).year(2016).artistsNumber(2).artists(arr.subList(1, 2)).build(),
                Song.builder().id(13L).title("Meneame").genre("Pop, Latin Urban").type(SongType.ORIGINAL).year(2015).artistsNumber(1).artists(arr.subList(2, 3)).build(),
                Song.builder().id(14L).title("Pressure").genre("Pop").type(SongType.ORIGINAL).year(2022).artistsNumber(1).artists(arr.subList(2, 3)).build(),
                Song.builder().id(15L).title("Just Shutup and Dance").genre("Pop").type(SongType.ORIGINAL).year(2021).artistsNumber(1).artists(arr.subList(2, 3)).build(),
                Song.builder().id(16L).title("Peaches").genre("Pop").type(SongType.ORIGINAL).year(2021).artistsNumber(1).artists(arr.subList(3, 4)).build(),
                Song.builder().id(17L).title("Love Yourself").genre("Pop").type(SongType.ORIGINAL).year(2015).artistsNumber(1).artists(arr.subList(3, 4)).build(),
                Song.builder().id(18L).title("Sorry").genre("Pop").type(SongType.ORIGINAL).year(2015).artistsNumber(1).artists(arr.subList(3, 4)).build(),
                Song.builder().id(19L).title("What Do You Mean?").genre("Pop").type(SongType.ORIGINAL).year(2015).artistsNumber(1).artists(arr.subList(3, 4)).build()
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