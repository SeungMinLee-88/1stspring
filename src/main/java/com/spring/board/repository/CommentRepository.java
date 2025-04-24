package com.spring.board.repository;

import com.spring.board.entity.BaseEntity;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.BoardFileEntity;
import com.spring.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
  // select * from comment_table where board_id=? order by id desc;
  List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);

  List<CommentEntity> findByBoardEntity(BoardEntity boardEntity);


  List<CommentEntity> findByBoardEntityAndCommentEntityIsNull(BoardEntity boardEntity);

  @Query(value ="SELECT ca.*, cb.* FROM comment_table ca\n" +
          "left outer join comment_table cb on ca.id = cb.parent_id\n" +
          "where ca.board_id = :boardId"
          , countQuery = "select count(*) FROM comment_table ca\\n\" +\n" +
          "          \"left outer join comment_table cb on ca.id = cb.parent_id\\n\" +\n" +
          "          \"where ca.board_id = :boardId"
          ,nativeQuery = true)
  List<CommentEntity> findByBoardId(long boardId);
}
