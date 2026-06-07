package com.loutredev.conf_game_backend.exceptions.ressource;

public class BingoNotFoundException extends ResourceNotFoundException {

  public BingoNotFoundException(Long id) {
    super("Bingo : " + id + " is not found");
  }
}
