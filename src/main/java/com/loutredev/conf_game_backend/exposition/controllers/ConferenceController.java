package com.loutredev.conf_game_backend.exposition.controllers;

import com.loutredev.conf_game_backend.domain.services.ConferenceService;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.ConferenceQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.ConferenceMapper;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conference")
public class ConferenceController {

  @Autowired
  private ConferenceService conferenceService;

  @GetMapping("/{id}")
  public ResponseEntity<ConferenceResponseDTO> getById(@PathVariable Long id) {
    ConferenceResponseDTO response = conferenceService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/search")
  public ResponseEntity<Map<String, Object>> getAll(ConferenceQueryDTO query) {
    Page<ConferenceEntity> result = conferenceService.getAll(query);

    Map<String, Object> response = new HashMap<>();
    response.put("content", result.getContent().stream().map(ConferenceMapper::toDto).toList());
    response.put("currentPage", result.getNumber());
    response.put("totalItems", result.getTotalElements());
    response.put("totalPages", result.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ConferenceResponseDTO> create(@Valid @RequestBody ConferenceRequestCreateDTO dto) {
    ConferenceResponseDTO saved = conferenceService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ConferenceResponseDTO> updatePartial(@PathVariable Long id, @Valid @RequestBody ConferenceRequestUpdateDTO dto) {
    ConferenceResponseDTO updated = conferenceService.update(dto);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    conferenceService.delete(id);
  }
}
