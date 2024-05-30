package com.soundlab.repository;

import com.soundlab.domain.Listening;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListeningRepository extends JpaRepository<Listening, Long> {

    @Query("SELECT l FROM Listening l ORDER BY l.createdAt DESC")
    List<Listening> findLastFour(Pageable pageable);

}