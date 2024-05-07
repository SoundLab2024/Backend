package com.soundlab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "playlist")

public class Playlist extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il nome è richiesto")
    @NotBlank(message = "Il nome è richiesto")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Il numero delle tracce è richiesto")
    @Column(name = "songs_number", columnDefinition = "integer default 0")
    private Integer songsNumber;

    @Column(name = "genre")
    private String genre;

    @NotNull (message = "La preferenza delle tracce è richiesta")
    @Column(name = "favourite", columnDefinition = "boolean default false")
    private boolean favourite;

    @ManyToOne
    @JoinColumn(name = "library_id")
    @NotNull(message = "Il riferimento alla libreria è richiesto")
    private Library library;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "adds",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private List<Song> songs;
}
