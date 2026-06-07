package com.loutredev.conf_game_backend.persistence.repositories;

import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  Optional<UserEntity> findByEmail(String email);
  boolean existsByEmail(String email);
}
