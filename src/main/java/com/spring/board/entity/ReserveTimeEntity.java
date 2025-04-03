package com.spring.board.entity;

import com.spring.board.dto.BoardDTO;
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
@Table(name = "reserveTime")
public class ReserveTimeEntity extends BaseEntity {
  @Id // pk 컬럼 지정. 필수
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
  private Long id;


  @ManyToMany
  @JoinTable(
          name = "ReserveEntity ",
          joinColumns = @JoinColumn(name = "reserveTimeId"),
          inverseJoinColumns = @JoinColumn(name = "reserveId")
  )
  private List<ReserveEntity> reserveEntity = new ArrayList<>();


}
