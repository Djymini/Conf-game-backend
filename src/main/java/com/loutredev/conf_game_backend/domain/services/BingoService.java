package com.loutredev.conf_game_backend.domain.services;

import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.PAGE_SIZE;

import com.loutredev.conf_game_backend.annotations.validate.ValidateBingo; // Supposant que vous avez cette annotation
import com.loutredev.conf_game_backend.exceptions.delete_ressource.BingoNotDelete;
import com.loutredev.conf_game_backend.exceptions.ressource.UserNotFoundException;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestCreatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestUpdatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.BingoQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.BingoMapper;
import com.loutredev.conf_game_backend.persistence.entities.BingoEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.BingoRepository;
import com.loutredev.conf_game_backend.persistence.repositories.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BingoService {

  @Autowired
  private BingoRepository bingoRepository;

  @Autowired
  private UserRepository userRepository;

  @ValidateBingo
  public BingoResponseDTO getById(Long id) {
    BingoEntity entity = bingoRepository.findById(id).get();
    return BingoMapper.toDto(entity);
  }

  public Page<BingoEntity> getAllByUserId(UUID userId, BingoQueryDTO query) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    Pageable pageable = PageRequest.of(query.page, PAGE_SIZE);
    return bingoRepository.findBingoByUser(userId, query.keywprd, query.startDate, query.endDate, pageable);
  }

  public BingoResponseDTO create(BingoRequestCreatedDTO dto) {
    if (!userRepository.existsById(dto.userId())) {
      throw new UserNotFoundException(dto.userId());
    }
    UserEntity user = userRepository.findById(dto.userId()).get();
    BingoEntity entity = BingoMapper.toEntity(dto, user);

    BingoEntity saved = bingoRepository.save(entity);
    return BingoMapper.toDto(saved);
  }

  @ValidateBingo
  public BingoResponseDTO update(BingoRequestUpdatedDTO dto) {
    BingoEntity entity = bingoRepository.findById(dto.id()).get();
    entity.setTitle(dto.title());

    if (dto.proposals() != null) {
      entity.getProposals().clear();
      entity.getProposals().addAll(dto.proposals());
    }

    BingoEntity updated = bingoRepository.save(entity);
    return BingoMapper.toDto(updated);
  }

  @ValidateBingo
  public void delete(Long id) {
    bingoRepository.deleteById(id);
    if (bingoRepository.existsById(id)) {
      throw new BingoNotDelete(id);
    }
  }
}
