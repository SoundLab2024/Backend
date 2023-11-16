package com.soundlab.repository;
import com.soundlab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User related repository for the underlying postgres database
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> { }