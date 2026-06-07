package com.loutredev.conf_game_backend.persistence.entities;

import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_25;

import com.loutredev.conf_game_backend.enums.SessionStatus;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "session")
@Getter
@Setter
public class SessionEntity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = VARCHAR_25)
  private SessionStatus status = SessionStatus.INCOMMING;

  @Column(nullable = true)
  private Integer limitParticipant;

  @Column(nullable = true)
  private Integer participant = 0;

  @ManyToOne
  @JoinColumn(name = "conference_id")
  private ConferenceEntity conference;

  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity users;

  @OneToMany(mappedBy = "session")
  private List<NoteEntity> notes = new ArrayList<>();
}
