package com.spring.board.repository;

import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  // select * from comment_table where board_id=? order by id desc;
  List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
