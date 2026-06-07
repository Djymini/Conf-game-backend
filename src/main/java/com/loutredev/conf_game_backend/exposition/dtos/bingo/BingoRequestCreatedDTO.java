package com.loutredev.conf_game_backend.exposition.dtos.bingo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record BingoRequestCreatedDTO(
  @NotBlank(message = "Title is required") String title,
  List<String> proposals,
  @NotNull(message = "User id is required") UUID userId
) {}
