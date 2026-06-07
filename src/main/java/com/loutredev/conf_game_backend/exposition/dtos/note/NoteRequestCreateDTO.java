package com.loutredev.conf_game_backend.exposition.dtos.note;

import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_250;
import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_50;
import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.MIN_TEXT_SIZE;
import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.MIN_WORD_SIZE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record NoteRequestCreateDTO(
  @NotNull(message = "title is required")
  @NotBlank(message = "title can not be blank")
  @Size(min = MIN_WORD_SIZE, max = VARCHAR_50, message = "number of char in title must be between 2 and 50")
  String title,

  @NotNull(message = "content is required")
  @NotBlank(message = "content can not be blank")
  @Size(min = MIN_TEXT_SIZE, max = VARCHAR_250, message = "number of char in content must be between 5 and 250")
  String content,

  @NotNull(message = "Session id is required") Long sessionId,

  @NotNull(message = "Session id is required") UUID userId
) {}
