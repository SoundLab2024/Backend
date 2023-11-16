package com.soundlab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt", "updatedAt"},
        allowGetters = true
)
@Getter
@Setter
public abstract class AuditModel implements Serializable {

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "dd-MM-YYYY HH:MM")
    private LocalDateTime createdAt;


    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-YYYY HH:MM")
    private LocalDateTime updatedAt;
}
