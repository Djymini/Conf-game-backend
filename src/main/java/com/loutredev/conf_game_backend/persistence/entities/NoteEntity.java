package com.loutredev.conf_game_backend.persistence.entities;

import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_250;
import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_50;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "note")
@Getter
@Setter
public class NoteEntity extends BaseEntity {

  @Column(nullable = false, length = VARCHAR_50)
  private String title;

  @Column(nullable = false, length = VARCHAR_250)
  private String content;

  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity users;

  @ManyToOne
  @JoinColumn(name = "session_id")
  private SessionEntity session;
}
