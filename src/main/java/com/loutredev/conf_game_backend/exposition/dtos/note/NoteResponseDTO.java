package com.loutredev.conf_game_backend.exposition.dtos.note;

import java.time.LocalDateTime;
import java.util.UUID;

public record NoteResponseDTO(Long id, String title, String content, UUID userId, Long sessionId, LocalDateTime createdAt, LocalDateTime updatedAt) {}
