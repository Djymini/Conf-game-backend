package com.loutredev.conf_game_backend.domain.services;

import static com.loutredev.conf_game_backend.utilitis.Constant.RequestDefaut.PAGE_SIZE;

import com.loutredev.conf_game_backend.annotations.validate.ValidateNote;
import com.loutredev.conf_game_backend.annotations.validate.ValidateSession;
import com.loutredev.conf_game_backend.exceptions.delete_ressource.NoteNotDelete;
import com.loutredev.conf_game_backend.exceptions.ressource.UserNotFoundException;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.NoteQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.NoteMapper;
import com.loutredev.conf_game_backend.persistence.entities.NoteEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.NoteRepository;
import com.loutredev.conf_game_backend.persistence.repositories.SessionRepository;
import com.loutredev.conf_game_backend.persistence.repositories.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private UserRepository userRepository;

  @ValidateNote
  public NoteResponseDTO getById(Long id) {
    NoteEntity entity = noteRepository.findById(id).get();
    return NoteMapper.toDto(entity);
  }

  public Page<NoteEntity> getAllByUserId(UUID id, NoteQueryDTO query) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    Pageable pageable = PageRequest.of(query.page, PAGE_SIZE);
    return noteRepository.findByUserId(id, query.keywprd, query.startDate, query.endDate, pageable);
  }

  @ValidateSession
  public Page<NoteEntity> getAllBySessionId(Long id, NoteQueryDTO query) {
    Pageable pageable = PageRequest.of(query.page, PAGE_SIZE);
    return noteRepository.findBysessionId(id, query.keywprd, query.startDate, query.endDate, pageable);
  }

  @ValidateSession(idField = "sessionId")
  public NoteResponseDTO create(NoteRequestCreateDTO dto) {
    if (!userRepository.existsById(dto.userId())) {
      throw new UserNotFoundException(dto.userId());
    }
    UserEntity user = userRepository.findById(dto.userId()).get();
    SessionEntity session = sessionRepository.findById(dto.sessionId()).get();
    NoteEntity entity = NoteMapper.toEntity(dto, session, user);

    NoteEntity saved = noteRepository.save(entity);
    return NoteMapper.toDto(saved);
  }

  @ValidateNote
  public NoteResponseDTO update(NoteRequestUpdateDTO dto) {
    NoteEntity entity = noteRepository.findById(dto.id()).get();
    entity.setTitle(dto.title());
    entity.setContent(dto.content());
    NoteEntity updated = noteRepository.save(entity);
    return NoteMapper.toDto(updated);
  }

  @ValidateNote
  public void delete(Long id) {
    noteRepository.deleteById(id);
    if (noteRepository.existsById(id)) {
      throw new NoteNotDelete(id);
    }
  }
}
