package com.loutredev.conf_game_backend.exceptions.ressource;

public class ConferenceNotFoundException extends ResourceNotFoundException {

  public ConferenceNotFoundException(Long id) {
    super("Conference : " + id + " is not found");
  }
}
