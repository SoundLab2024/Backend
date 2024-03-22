package com.soundlab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artist")

public class Artist extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Il nome è richiesto")
    @NotBlank(message = "Il nome è richiesto")
    private String nome;

    private LocalDate dataNascita;

    @NotNull(message = "La nazionalita è richiesta")
    @NotBlank(message = "La nazionalita è richiesta")
    private String nazionalita;

    @OneToMany (mappedBy = "artist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Album> album;

    @ManyToMany(mappedBy = "artist", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Traccia> traccia;

}
