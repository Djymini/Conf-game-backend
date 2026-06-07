package com.loutredev.conf_game_backend.exceptions.delete_ressource;

public class ConferenceNotDelete extends ResourceNotDelete {

  public ConferenceNotDelete(Long id) {
    super("Erreur lors de la suppression de la conference : " + id);
  }
}
