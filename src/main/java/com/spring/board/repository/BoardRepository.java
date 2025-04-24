package com.spring.board.repository;

import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long>, JpaSpecificationExecutor<BoardEntity> {
  // update board_table set board_hits=board_hits+1 where id=?
  /*@Query(value ="select b.* from board b :wheretext order by :sortfield asc"
          , countQuery = "select count(*) from board b"
          ,nativeQuery = true)
  Page<BoardEntity> findAllboardWithParameterPagination(Pageable pageable, String sortfield, String wheretext);*/

  @Modifying
  @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id") // 엔티티기준
  void updateHits(@Param("id") Long id);

  @Modifying
  @Query(value = "update BoardEntity b set b.fileAttached=0 where b.id=:id") // 엔티티기준
  void updatefileAttached(@Param("id") Long id);
}
