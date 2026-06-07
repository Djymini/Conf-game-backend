package com.loutredev.conf_game_backend.exposition.dtos.bingo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record BingoRequestUpdatedDTO(
  @NotNull(message = "Id is required") Long id,
  @NotBlank(message = "Title is required") String title,
  List<String> proposals
) {}
