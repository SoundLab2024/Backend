package com.soundlab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "libraries")

public class Library extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "playlists_number")
    private Integer playlistsNumber;

    @OneToMany(mappedBy = "library", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Playlist> playlists;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    @NotNull(message = "Il riferimento all'utente è richiesto")
    private User user;
}
