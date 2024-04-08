package com.soundlab.domain;

import com.soundlab.domain.properties.SongType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "song")

public class Song extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il titolo è richiesto")
    @NotBlank(message = "Il titolo è richiesto")
    @Column(name = "title")
    private String title;

    @NotNull(message = "L'anno è richiesto")
    @Column(name = "year")
    private Integer year;

    @NotNull(message = "Il genere è richiesto")
    @NotBlank(message = "Il genere è richiesto")
    @Column(name = "genre")
    private String genre;

    @NotNull(message = "Il tipo della traccia è richiesto")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SongType type;

    @Column(name = "artists_number")
    private Integer artistsNumber;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToMany(mappedBy = "song", fetch = FetchType.LAZY)
    private Set<Listening> listenings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "collabs",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @NotNull(message = "I riferimenti agli artisti sono richiesti")
    private Set<Artist> artists;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "adds",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlists;

}
