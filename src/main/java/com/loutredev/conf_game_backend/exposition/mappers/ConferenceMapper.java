package com.loutredev.conf_game_backend.exposition.mappers;

import com.loutredev.conf_game_backend.enums.ConferenceStatus;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceResponseDTO;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;

public class ConferenceMapper {

  public static ConferenceEntity toEntity(ConferenceRequestCreateDTO dto) {
    ConferenceEntity entity = new ConferenceEntity();
    entity.setName(dto.name());
    entity.setDescription(dto.description());
    entity.setOrganisator(dto.organisator());
    entity.setStreamDate(dto.streamDate());
    entity.setLinkStream(dto.linkStream());
    entity.setImageUrl(dto.imageUrl());
    entity.setStatus(ConferenceStatus.valueOf(dto.status()));
    return entity;
  }

  public static ConferenceResponseDTO toDto(ConferenceEntity entity) {
    return new ConferenceResponseDTO(
      entity.getId(),
      entity.getName(),
      entity.getDescription(),
      entity.getOrganisator(),
      entity.getStreamDate(),
      entity.getLinkStream(),
      entity.getImageUrl(),
      entity.getStatus().toString(),
      entity.getWatchNumber(),
      entity.getCreatedAt(),
      entity.getUpdatedAt()
    );
  }
}
