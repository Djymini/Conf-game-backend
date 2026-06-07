package com.loutredev.conf_game_backend.persistence.repositories;

import com.loutredev.conf_game_backend.enums.ConferenceStatus;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<ConferenceEntity, Long> {
  //CHECKSTYLE:OFF
  @Query(
    "SELECT c FROM ConferenceEntity c WHERE " +
      "(:keyword IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:status IS NULL OR c.status = :status) " +
      "AND (:startDate IS NULL OR c.createdAt >= :startDate) " +
      "AND (:endDate IS NULL OR c.createdAt <= :endDate)"
  )
  Page<ConferenceEntity> searchConferences(
    @Param("keyword") String keyword,
    @Param("status") ConferenceStatus status,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    Pageable pageable
  );
  //CHECKSTYLE:ON
}
