package com.soundlab.repository;

import com.soundlab.domain.Listening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListeningRepository extends JpaRepository<Listening, Long> { }