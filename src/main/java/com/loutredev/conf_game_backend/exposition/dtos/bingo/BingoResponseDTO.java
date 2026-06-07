package com.loutredev.conf_game_backend.exposition.dtos.bingo;

import java.util.List;
import java.util.UUID;

public record BingoResponseDTO(Long id, String title, List<String> proposals, UUID userId) {}
