package com.soundlab.domain;

import com.soundlab.domain.properties.TracciaType;
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
@Table(name = "traccia")

public class Traccia extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il titolo è richiesto")
    @NotBlank(message = "Il titolo è richiesto")
    private String titolo;

    @NotNull(message = "L'anno è richiesto")
    private Integer anno;

    @NotNull(message = "Il genere è richiesto")
    @NotBlank(message = "Il genere è richiesto")
    private String genere;

    @NotNull(message = "Il tipo della traccia è richiesto")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TracciaType type;

    @Column(name = "numero_artisti")
    private Integer numeroArtisti;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @OneToMany(mappedBy = "traccia", fetch = FetchType.EAGER)
    private Set<Ascolto> ascolto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "collab",
            joinColumns = @JoinColumn(name = "traccia_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    @NotNull(message = "I riferimenti agli artisti sono richiesti")
    private Set<Artist> artist;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "aggiungi",
            joinColumns = @JoinColumn(name = "traccia_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private Set<Playlist> playlist;

}
