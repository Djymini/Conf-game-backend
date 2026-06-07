package com.loutredev.conf_game_backend.domain.services;

import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.PAGE_SIZE;

import com.loutredev.conf_game_backend.annotations.validate.ValidateConference;
import com.loutredev.conf_game_backend.enums.ConferenceStatus;
import com.loutredev.conf_game_backend.exceptions.delete_ressource.ConferenceNotDelete;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.ConferenceQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.ConferenceMapper;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConferenceService {

  @Autowired
  private ConferenceRepository conferenceRepository;

  @ValidateConference
  public ConferenceResponseDTO getById(Long id) {
    ConferenceEntity entity = conferenceRepository.findById(id).get();
    return ConferenceMapper.toDto(entity);
  }

  public Page<ConferenceEntity> getAll(ConferenceQueryDTO query) {
    Pageable pageable = PageRequest.of(query.page, PAGE_SIZE);
    return conferenceRepository.searchConferences(query.keywprd, query.status, query.startDate, query.endDate, pageable);
  }

  public ConferenceResponseDTO create(ConferenceRequestCreateDTO dto) {
    ConferenceEntity entity = ConferenceMapper.toEntity(dto);
    if (dto.status() != null) {
      entity.setStatus(ConferenceStatus.valueOf(dto.status()));
    }
    ConferenceEntity saved = conferenceRepository.save(entity);
    return ConferenceMapper.toDto(saved);
  }

  @ValidateConference
  public ConferenceResponseDTO update(ConferenceRequestUpdateDTO dto) {
    ConferenceEntity entity = conferenceRepository.findById(dto.id()).get();
    entity.setName(dto.name());
    entity.setDescription(dto.description());
    entity.setOrganisator(dto.organisator());
    entity.setStreamDate(dto.streamDate());
    entity.setLinkStream(dto.linkStream());
    entity.setImageUrl(dto.imageUrl());
    if (dto.status() != null) {
      entity.setStatus(ConferenceStatus.valueOf(dto.status()));
    }
    entity.setWatchNumber(dto.watchNumber());

    ConferenceEntity updated = conferenceRepository.save(entity);
    return ConferenceMapper.toDto(updated);
  }

  @ValidateConference
  public void delete(Long id) {
    conferenceRepository.deleteById(id);
    if (conferenceRepository.existsById(id)) {
      throw new ConferenceNotDelete(id);
    }
  }
}
