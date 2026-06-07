package com.loutredev.conf_game_backend.exceptions.ressource;

public class NoteNotFoundException extends ResourceNotFoundException {

  public NoteNotFoundException(Long id) {
    super("Note : " + id + " is not found");
  }
}
