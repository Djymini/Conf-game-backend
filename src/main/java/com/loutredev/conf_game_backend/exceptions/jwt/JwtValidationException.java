package com.loutredev.conf_game_backend.exceptions.jwt;

public class JwtValidationException extends RuntimeException {

  public JwtValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
