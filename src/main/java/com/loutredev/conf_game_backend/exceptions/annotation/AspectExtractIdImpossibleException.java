package com.loutredev.conf_game_backend.exceptions.annotation;

public class AspectExtractIdImpossibleException extends RuntimeException {

  public AspectExtractIdImpossibleException(Class aspect) {
    super("Extraction de l'id impossible pour : " + aspect.getName());
  }
}
