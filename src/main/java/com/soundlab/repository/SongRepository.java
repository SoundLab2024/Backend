package com.soundlab.repository;

import com.soundlab.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s WHERE LOWER(s.title) LIKE CONCAT(LOWER(:prefix), '%')")
    List<Song> findByTitleStartingWith(@Param("prefix") String prefix);

    @Query("SELECT s FROM Song s WHERE s.title = :prefix")
    Song findByExactTitle(@Param("prefix") String prefix);

    @Query("SELECT s FROM Song s WHERE s.genre = :prefix")
    List<Song> findAllByGenre(@Param("prefix") String prefix);

}