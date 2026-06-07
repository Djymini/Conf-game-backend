package com.loutredev.conf_game_backend.exposition.dtos.conference;

import java.time.LocalDateTime;

public record ConferenceResponseDTO(
  Long id,
  String name,
  String description,
  String organisator,
  LocalDateTime streamDate,
  String linnkStream,
  String imageUrl,
  String status,
  Integer watchNumber,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
