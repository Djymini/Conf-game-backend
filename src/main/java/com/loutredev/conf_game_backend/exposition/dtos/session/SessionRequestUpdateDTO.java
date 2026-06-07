package com.loutredev.conf_game_backend.exposition.dtos.session;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SessionRequestUpdateDTO(
  Long id,

  String status,

  @Positive(message = "Can not be negative") Integer limitParticipant,

  @Positive(message = "Can not be negative") Integer participant,

  @NotNull(message = "Conference id is required") Long conferenceId
) {}
