package com.soundlab.repository;

import com.soundlab.domain.Song;
import com.soundlab.dto.SongDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s WHERE s.title LIKE :prefix%")
    List<SongDTO> findByTitleStartingWith(@Param("prefix") String prefix);

}