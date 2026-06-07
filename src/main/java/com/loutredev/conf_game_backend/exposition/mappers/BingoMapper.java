package com.loutredev.conf_game_backend.exposition.mappers;

import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestCreatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoResponseDTO;
import com.loutredev.conf_game_backend.persistence.entities.BingoEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;

public class BingoMapper {

  public static BingoEntity toEntity(BingoRequestCreatedDTO dto, UserEntity user) {
    BingoEntity entity = new BingoEntity();
    entity.setTitle(dto.title());
    entity.setUsers(user);
    return entity;
  }

  public static BingoResponseDTO toDto(BingoEntity entity) {
    return new BingoResponseDTO(entity.getId(), entity.getTitle(), entity.getProposals(), entity.getUsers().getId());
  }
}
