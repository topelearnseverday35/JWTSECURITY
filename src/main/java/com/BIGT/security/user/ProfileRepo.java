package com.BIGT.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<ProfileCreationDb, Long> {
    Optional<ProfileCreationDb> findByEmail(String email);
}
