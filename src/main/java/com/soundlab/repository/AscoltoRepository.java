package com.soundlab.repository;

import com.soundlab.domain.Ascolto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AscoltoRepository extends JpaRepository<Ascolto, Long> { }