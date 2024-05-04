package com.soundlab.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

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
    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birth;

    @NotNull(message = "La nazionalita è richiesta")
    @NotBlank(message = "La nazionalita è richiesta")
    @Column(name = "nationality")
    private String nationality;

    @OneToMany (mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Album> albums;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Song> songs;

}
