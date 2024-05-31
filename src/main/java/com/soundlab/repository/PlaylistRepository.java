package com.soundlab.repository;

import com.soundlab.domain.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query("SELECT p FROM Playlist p WHERE p.library.id = :libId")
    List<Playlist> findPlaylistsByLib(@Param("libId") Long libId);

}