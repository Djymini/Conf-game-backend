package com.loutredev.conf_game_backend.exceptions.delete_ressource;

public class BingoNotDelete extends ResourceNotDelete {

  public BingoNotDelete(Long id) {
    super("Erreur lors de la suppression du bingo : " + id);
  }
}
