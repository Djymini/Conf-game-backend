package com.loutredev.conf_game_backend.exposition.controllers;

import com.loutredev.conf_game_backend.domain.services.BingoService;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestCreatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestUpdatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.BingoQueryDTO;
import com.loutredev.conf_game_backend.exposition.mappers.BingoMapper;
import com.loutredev.conf_game_backend.persistence.entities.BingoEntity;
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
@RequestMapping("/bingo")
public class BingoController {

  @Autowired
  private BingoService bingoService;

  @GetMapping("/{id}")
  public ResponseEntity<BingoResponseDTO> getById(@PathVariable Long id) {
    BingoResponseDTO response = bingoService.getById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/user-list/{userId}")
  public ResponseEntity<Map<String, Object>> getAllByUserId(@PathVariable UUID userId, BingoQueryDTO query) {
    Page<BingoEntity> result = bingoService.getAllByUserId(userId, query);

    Map<String, Object> response = new HashMap<>();
    response.put("content", result.getContent().stream().map(BingoMapper::toDto).toList());
    response.put("currentPage", result.getNumber());
    response.put("totalItems", result.getTotalElements());
    response.put("totalPages", result.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<BingoResponseDTO> create(@Valid @RequestBody BingoRequestCreatedDTO dto) {
    BingoResponseDTO saved = bingoService.create(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BingoResponseDTO> updatePartial(@PathVariable Long id, @Valid @RequestBody BingoRequestUpdatedDTO dto) {
    BingoResponseDTO updated = bingoService.update(dto);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    bingoService.delete(id);
  }
}
