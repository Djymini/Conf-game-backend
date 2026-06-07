package com.loutredev.conf_game_backend.exposition.dtos.session;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record SessionRequestCreateDTO(
  String status,

  @Positive(message = "Can not be negative") Integer limitParticipant,

  @NotNull(message = "Conference id is required") Long conferenceId,

  @NotNull(message = "Conference id is required") UUID userId
) {}
