package com.loutredev.conf_game_backend.domain.services;

import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.PAGE_SIZE;

import com.loutredev.conf_game_backend.annotations.validate.ValidateConference;
import com.loutredev.conf_game_backend.annotations.validate.ValidateSession;
import com.loutredev.conf_game_backend.domain.rules.SessionRules;
import com.loutredev.conf_game_backend.enums.SessionStatus;
import com.loutredev.conf_game_backend.exceptions.delete_ressource.SessionNotDelete;
import com.loutredev.conf_game_backend.exceptions.ressource.UserNotFoundException;
import com.loutredev.conf_game_backend.exposition.dtos.queries.SessionQueryDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionResponseDTO;
import com.loutredev.conf_game_backend.exposition.mappers.SessionMapper;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import com.loutredev.conf_game_backend.persistence.repositories.SessionRepository;
import com.loutredev.conf_game_backend.persistence.repositories.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private ConferenceRepository conferenceRepository;

  @Autowired
  private UserRepository userRepository;

  @ValidateSession
  public SessionResponseDTO getById(Long id) {
    SessionEntity entity = sessionRepository.findById(id).get();
    return SessionMapper.toDto(entity);
  }

  public Page<SessionEntity> getAllByUserId(UUID id, SessionQueryDTO query) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }

    Pageable pageable = PageRequest.of(query.page, PAGE_SIZE);
    return sessionRepository.findSessionByUser(id, query.keywprd, query.status, query.startDate, query.endDate, pageable);
  }

  @ValidateConference(idField = "conferenceId")
  public SessionResponseDTO create(SessionRequestCreateDTO dto) {
    if (!userRepository.existsById(dto.userId())) {
      throw new UserNotFoundException(dto.userId());
    }
    UserEntity user = userRepository.findById(dto.userId()).get();
    ConferenceEntity conference = conferenceRepository.findById(dto.conferenceId()).get();
    SessionEntity entity = SessionMapper.toEntity(dto, conference, user);
    SessionRules.customStatusForCreation(entity);

    SessionEntity saved = sessionRepository.save(entity);
    return SessionMapper.toDto(saved);
  }

  @ValidateSession
  public SessionResponseDTO update(SessionRequestUpdateDTO dto) {
    SessionEntity entity = sessionRepository.findById(dto.id()).get();
    entity.setStatus(SessionStatus.valueOf(dto.status()));
    entity.setLimitParticipant(dto.limitParticipant());
    entity.setParticipant(dto.participant());

    SessionEntity updated = sessionRepository.save(entity);
    return SessionMapper.toDto(updated);
  }

  @ValidateSession
  public void delete(Long id) {
    sessionRepository.deleteById(id);
    if (!sessionRepository.existsById(id)) {
      throw new SessionNotDelete(id);
    }
  }
}
