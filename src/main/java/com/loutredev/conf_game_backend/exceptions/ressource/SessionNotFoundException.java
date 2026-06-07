package com.loutredev.conf_game_backend.exceptions.ressource;

public class SessionNotFoundException extends ResourceNotFoundException {

  public SessionNotFoundException(Long id) {
    super("Session : " + id + " is not found");
  }
}
