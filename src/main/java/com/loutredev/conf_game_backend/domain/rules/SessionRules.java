package com.loutredev.conf_game_backend.domain.rules;

import com.loutredev.conf_game_backend.enums.SessionStatus;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import java.time.LocalDateTime;

public class SessionRules {

  public static void customStatusForCreation(SessionEntity entity) {
    if (entity.getConference().getStreamDate().isBefore(LocalDateTime.now())) {
      entity.setStatus(SessionStatus.REDIFFUSION);
    } else {
      entity.setStatus(SessionStatus.INCOMMING);
    }
  }
}
