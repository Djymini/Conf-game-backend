package com.loutredev.conf_game_backend.exposition.mappers;

import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteResponseDTO;
import com.loutredev.conf_game_backend.persistence.entities.NoteEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;

public class NoteMapper {

  public static NoteEntity toEntity(NoteRequestCreateDTO dto, SessionEntity session, UserEntity user) {
    NoteEntity entity = new NoteEntity();
    entity.setTitle(dto.title());
    entity.setContent(dto.content());
    entity.setSession(session);
    entity.setUsers(user);
    return entity;
  }

  public static NoteResponseDTO toDto(NoteEntity entity) {
    return new NoteResponseDTO(
      entity.getId(),
      entity.getTitle(),
      entity.getContent(),
      entity.getUsers().getId(),
      entity.getSession().getId(),
      entity.getCreatedAt(),
      entity.getUpdatedAt()
    );
  }
}
