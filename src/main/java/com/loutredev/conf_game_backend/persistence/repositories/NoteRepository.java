package com.loutredev.conf_game_backend.persistence.repositories;

import com.loutredev.conf_game_backend.persistence.entities.NoteEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
  //CHECKSTYLE:OFF
  @Query(
    "SELECT n FROM NoteEntity n WHERE " +
      "n.session.id = :sessionId " + // Espace ici
      "AND (:keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
      "AND (:startDate IS NULL OR n.createdAt >= :startDate) " +
      "AND (:endDate IS NULL OR n.createdAt <= :endDate)"
  )
  Page<NoteEntity> findBysessionId(
    @Param("sessionId") Long sessionId,
    @Param("keyword") String keyword,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    Pageable pageable
  );

  //CHECKSTYLE:ON

  //CHECKSTYLE:OFF
  @Query(
    "SELECT n FROM NoteEntity n WHERE " +
      "n.users.id = :userId " +
      "AND (:keyword IS NULL OR LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " + // Changé c.name en n.title
      "AND (:startDate IS NULL OR n.createdAt >= :startDate) " +
      "AND (:endDate IS NULL OR n.createdAt <= :endDate)"
  )
  Page<NoteEntity> findByUserId(
    @Param("userId") UUID userId,
    @Param("keyword") String keyword,
    @Param("startDate") LocalDateTime startDate,
    @Param("endDate") LocalDateTime endDate,
    Pageable pageable
  );
  //CHECKSTYLE:ON
}
