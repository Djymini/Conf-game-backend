package com.loutredev.conf_game_backend.exposition.controllers;

import com.loutredev.conf_game_backend.domain.services.NoteService;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.NoteQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.NoteMapper;
import com.loutredev.conf_game_backend.persistence.entities.NoteEntity;
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
@RequestMapping("/note")
public class NoteController {

  @Autowired
  private NoteService noteService;

  @GetMapping("/{id}")
  public ResponseEntity<NoteResponseDTO> getById(@PathVariable Long id) {
    NoteResponseDTO response = noteService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user-list/{userId}")
  public ResponseEntity<Map<String, Object>> getAllByUserId(@PathVariable UUID userId, NoteQueryDTO query) {
    Page<NoteEntity> result = noteService.getAllByUserId(userId, query);

    Map<String, Object> response = new HashMap<>();
    response.put("content", result.getContent().stream().map(NoteMapper::toDto).toList());
    response.put("currentPage", result.getNumber());
    response.put("totalItems", result.getTotalElements());
    response.put("totalPages", result.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/session-list/{sessionId}")
  public ResponseEntity<Map<String, Object>> getAllByUserId(@PathVariable Long sessionId, NoteQueryDTO query) {
    Page<NoteEntity> result = noteService.getAllBySessionId(sessionId, query);

    Map<String, Object> response = new HashMap<>();
    response.put("content", result.getContent().stream().map(NoteMapper::toDto).toList());
    response.put("currentPage", result.getNumber());
    response.put("totalItems", result.getTotalElements());
    response.put("totalPages", result.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<NoteResponseDTO> create(@Valid @RequestBody NoteRequestCreateDTO dto) {
    NoteResponseDTO saved = noteService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<NoteResponseDTO> updatePartial(@PathVariable Long id, @Valid @RequestBody NoteRequestUpdateDTO dto) {
    NoteResponseDTO upadated = noteService.update(dto);
    return ResponseEntity.status(HttpStatus.OK).body(upadated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteByHealthRecordId(@PathVariable Long id) {
    noteService.delete(id);
  }
}
