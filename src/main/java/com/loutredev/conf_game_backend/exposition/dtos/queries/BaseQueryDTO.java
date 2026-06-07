package com.loutredev.conf_game_backend.exposition.dtos.queries;

import java.time.LocalDateTime;

public class BaseQueryDTO {

  public int page;
  public String keywprd;
  public LocalDateTime startDate;
  public LocalDateTime endDate;
}
