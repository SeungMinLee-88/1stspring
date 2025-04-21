package com.spring.board.repository;

import com.spring.board.entity.BoardFileEntity;
import com.spring.board.entity.ReserveTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

    @Query(value ="select * from board_file_table where board_id = :boardId"
            , countQuery = "select count(*) from board_file_table where board_id = :boardId"
            ,nativeQuery = true)
    List<BoardFileEntity> findByBoardId(long boardId);
}