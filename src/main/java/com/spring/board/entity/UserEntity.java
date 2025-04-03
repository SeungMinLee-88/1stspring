package com.spring.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
/*  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;*/

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id")
  private ReserveEntity reserveEntity;

  private String username;
  private String password;

  private String role;


}
