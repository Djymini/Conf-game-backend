package com.loutredev.conf_game_backend.persistence.repositories;

import com.loutredev.conf_game_backend.enums.ConferenceStatus;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
  //CHECKSTYLE:OFF
  @Query(
    "SELECT session FROM SessionEntity session WHERE " +
      "session.users.id = :userId " +
      "AND (:keyword IS NULL OR LOWER(session.conference.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:status IS NULL OR session.status = :status) " +
      "AND (:startDate IS NULL OR session.createdAt >= :startDate) " +
      "AND (:endDate IS NULL OR session.createdAt <= :endDate)"
  )
  Page<SessionEntity> findSessionByUser(
    @Param("userId") UUID userId,
    @Param("keyword") String keyword,
    @Param("status") ConferenceStatus status,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    Pageable pageable
  );
  //CHECKSTYLE:ON
}
