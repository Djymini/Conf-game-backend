package com.loutredev.conf_game_backend.persistence.entities;

import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.*;

import com.loutredev.conf_game_backend.enums.ConferenceStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "conference")
@Getter
@Setter
public class ConferenceEntity extends BaseEntity {

  @Column(nullable = false, length = VARCHAR_50)
  private String name;

  @Column(nullable = false, length = VARCHAR_250)
  private String description;

  @Column(nullable = false, length = VARCHAR_100)
  private String organisator;

  @Column(nullable = false)
  private LocalDateTime streamDate;

  @Column(nullable = false, length = VARCHAR_150)
  private String linkStream;

  @Column(nullable = false, length = VARCHAR_150)
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = VARCHAR_50)
  private ConferenceStatus status = ConferenceStatus.INCOMMING;

  @Column(nullable = true)
  private Integer watchNumber = 0;
}
