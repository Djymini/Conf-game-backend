package com.loutredev.conf_game_backend.exceptions.ressource;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
