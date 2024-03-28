package com.soundlab.domain;

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
@Table(name = "albums")

public class Album extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il nome è richiesto")
    @NotBlank(message = "Il nome è richiesto")
    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    @NotNull(message = "Il riferimento all'artista è richiesto")
    private Artist artist;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Song> songs;
}
