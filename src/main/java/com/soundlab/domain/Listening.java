package com.soundlab.domain;

import com.soundlab.domain.properties.TimeSlot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listenings")

public class Listening extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "la data è richiesta")
    @Column(name = "data")
    @DateTimeFormat(pattern = "dd-MM-YYYY HH:MM")
    private Date data;

    @NotNull(message = "La fascia oraria è richiesta")
    @Enumerated(EnumType.STRING)
    @Column(name = "time_slot")
    private TimeSlot timeSlot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "Il riferimento all'utente è richiesto")
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @NotNull(message = "Il riferimento alla traccia è richiesto")
    private Song song;

}
