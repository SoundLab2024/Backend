package com.soundlab.domain;

import com.soundlab.domain.properties.FasciaOrariaType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ascolto")

public class Ascolto extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "la data è richiesta")
    private LocalDateTime data;

    @NotNull(message = "La fascia oraria è richiesta")
    @Enumerated(EnumType.STRING)
    @Column(name = "fascia_oraria")
    private FasciaOrariaType fasciaOraria;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Il riferimento all'utente è richiesto")
    private User user;

    @ManyToOne
    @JoinColumn(name = "traccia_id")
    @NotNull(message = "Il riferimento alla traccia è richiesto")
    private Traccia traccia;

}
