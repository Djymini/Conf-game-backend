package com.loutredev.conf_game_backend.exceptions.ressource;

import java.util.UUID;

public class UserNotFoundException extends ResourceNotFoundException {

  public UserNotFoundException(UUID id) {
    super("Session : " + id + " is not found");
  }
}
