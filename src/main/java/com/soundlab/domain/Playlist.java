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
@Table(name = "playlist")

public class Playlist extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il nome è richiesto")
    @NotBlank(message = "Il nome è richiesto")
    private String nome;

    @NotNull(message = "Il numero delle tracce è richiesto")
    @Column(name = "numero_tracce")
    @Builder.Default
    private Integer numeroTracce = 0;

    private String genere;

    @NotNull
    private Boolean preferita = false;

    @ManyToOne
    @JoinColumn(name = "libreria_id")
    @NotNull(message = "Il riferimento alla libreria è richiesto")
    private Libreria libreria;

    @ManyToMany(mappedBy = "playlist", fetch = FetchType.EAGER)
    private Set<Traccia> traccia;
}
