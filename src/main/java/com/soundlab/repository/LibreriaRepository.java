package com.soundlab.repository;

import com.soundlab.domain.Libreria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibreriaRepository extends JpaRepository<Libreria, Long> { }
