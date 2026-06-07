package com.loutredev.conf_game_backend.exceptions.delete_ressource;

public class SessionNotDelete extends ResourceNotDelete {

  public SessionNotDelete(Long id) {
    super("Erreur lors de la suppression de la session : " + id);
  }
}
