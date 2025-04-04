package com.spring.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
/*@Setter*/
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserve")
public class ReserveEntity extends BaseEntity {
  @Id // pk 컬럼 지정. 필수
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
  private Long id;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  private UserEntity userEntity;

  @OneToMany(mappedBy = "reserveEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<ReserveTimeEntity> reserveTimeEntity = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hallId")
  private HallEntity hallEntity;

  @Data
  public class SearchCriteria {

    private String searchKey;
    private String searchValue;
  }

}
