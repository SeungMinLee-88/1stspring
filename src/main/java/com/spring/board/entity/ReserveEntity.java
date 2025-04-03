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

  @OneToMany(mappedBy = "reserveEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
  @Column(length = 10, nullable = false) // 크기 20, not null
  private List<UserEntity> reserveId = new ArrayList<>();

  @ManyToMany(mappedBy = "reserveEntity", fetch = FetchType.LAZY)
  private List<ReserveTimeEntity> reserveTimeEntities = new ArrayList<>();

  @Data
  public class SearchCriteria {

    private String searchKey;
    private String searchValue;
  }
}
