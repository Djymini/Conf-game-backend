package com.loutredev.conf_game_backend.exposition.dtos.session;

import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceResponseDTO;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessionResponseDTO(
  Long id,
  String status,
  Integer limitParticipant,
  Integer participant,
  ConferenceResponseDTO conference,
  UUID userId,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
