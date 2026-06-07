package com.loutredev.conf_game_backend.exposition.controllers;

import com.loutredev.conf_game_backend.domain.services.SessionService;
import com.loutredev.conf_game_backend.exposition.dtos.queries.SessionQueryDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionResponseDTO;
import com.loutredev.conf_game_backend.exposition.mappers.SessionMapper;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

  @Autowired
  private SessionService sessionService;

  @GetMapping("/{id}")
  public ResponseEntity<SessionResponseDTO> getById(@PathVariable Long id) {
    SessionResponseDTO response = sessionService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user-list/{userId}")
  public ResponseEntity<Map<String, Object>> getAllByUserId(@PathVariable UUID userId, SessionQueryDTO query) {
    Page<SessionEntity> result = sessionService.getAllByUserId(userId, query);

    Map<String, Object> response = new HashMap<>();
    response.put("content", result.getContent().stream().map(SessionMapper::toDto).toList());
    response.put("currentPage", result.getNumber());
    response.put("totalItems", result.getTotalElements());
    response.put("totalPages", result.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<SessionResponseDTO> create(@Valid @RequestBody SessionRequestCreateDTO dto) {
    SessionResponseDTO saved = sessionService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<SessionResponseDTO> updatePartial(@PathVariable Long id, @Valid @RequestBody SessionRequestUpdateDTO dto) {
    SessionResponseDTO upadated = sessionService.update(dto);
    return ResponseEntity.status(HttpStatus.OK).body(upadated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteByHealthRecordId(@PathVariable Long id) {
    sessionService.delete(id);
  }
}
