package com.loutredev.conf_game_backend.exposition.dtos.conference;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

public record ConferenceRequestUpdateDTO(
  @NotNull(message = "Id is required") Long id,
  @NotBlank(message = "Name is required") String name,
  @NotBlank(message = "Description is required") String description,
  @NotBlank(message = "Organisator is required") String organisator,
  @NotNull(message = "Stream date is required") LocalDateTime streamDate,
  String linkStream,
  String imageUrl,
  String status,
  @Positive(message = "Can not be negative") Integer watchNumber
) {}
