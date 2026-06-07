package com.loutredev.conf_game_backend.exposition.mappers;

import com.loutredev.conf_game_backend.enums.SessionStatus;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionResponseDTO;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;

public class SessionMapper {

  public static SessionEntity toEntity(SessionRequestCreateDTO dto, ConferenceEntity conference, UserEntity user) {
    SessionEntity entity = new SessionEntity();
    entity.setStatus(SessionStatus.valueOf(dto.status()));
    entity.setLimitParticipant(dto.limitParticipant());
    entity.setConference(conference);
    entity.setUsers(user);
    return entity;
  }

  public static SessionResponseDTO toDto(SessionEntity entity) {
    return new SessionResponseDTO(
      entity.getId(),
      entity.getStatus().toString(),
      entity.getLimitParticipant(),
      entity.getParticipant(),
      ConferenceMapper.toDto(entity.getConference()),
      entity.getUsers().getId(),
      entity.getCreatedAt(),
      entity.getUpdatedAt()
    );
  }
}
