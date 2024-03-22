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
@Table(name = "libreria")

public class Libreria extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_playlist")
    @Builder.Default
    private Integer numeroPlaylist = 0;

    @OneToMany(mappedBy = "libreria", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Playlist> playlist;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email")
    @NotNull(message = "Il riferimento all'utente è richiesto")
    private User user;
}
