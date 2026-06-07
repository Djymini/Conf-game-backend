package com.loutredev.conf_game_backend.exceptions.delete_ressource;

public class NoteNotDelete extends ResourceNotDelete {

  public NoteNotDelete(Long id) {
    super("Erreur lors de la suppression de la note : " + id);
  }
}
