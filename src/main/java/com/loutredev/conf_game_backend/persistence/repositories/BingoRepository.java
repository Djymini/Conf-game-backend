package com.loutredev.conf_game_backend.persistence.repositories;

import com.loutredev.conf_game_backend.persistence.entities.BingoEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BingoRepository extends JpaRepository<BingoEntity, Long> {
  //CHECKSTYLE:OFF
  @Query(
    "SELECT b FROM BingoEntity b WHERE " +
      "b.users.id = :userId " +
      "AND (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:startDate IS NULL OR b.createdAt >= :startDate) " +
      "AND (:endDate IS NULL OR b.createdAt <= :endDate)"
  )
  Page<BingoEntity> findBingoByUser(
    @Param("userId") UUID userId,
    @Param("keyword") String keyword,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    Pageable pageable
  );
  //CHECKSTYLE:ON
}
