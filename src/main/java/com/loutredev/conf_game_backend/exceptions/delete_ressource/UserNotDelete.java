package com.loutredev.conf_game_backend.exceptions.delete_ressource;

public class UserNotDelete extends ResourceNotDelete {

  public UserNotDelete(Long id) {
    super("Erreur lors de la suppression de l'utilisateur : " + id);
  }
}
