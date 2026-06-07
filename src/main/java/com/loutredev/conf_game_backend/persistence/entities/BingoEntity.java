package com.loutredev.conf_game_backend.persistence.entities;

import static com.loutredev.conf_game_backend.utilitis.Constant.EntityData.VARCHAR_50;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bingo")
@Getter
@Setter
public class BingoEntity extends BaseEntity {

  @Column(nullable = false, length = VARCHAR_50)
  private String title;

  @ElementCollection
  @CollectionTable(name = "bingo_proposals", joinColumns = @JoinColumn(name = "bingo_id"))
  private List<String> proposals = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity users;
}
