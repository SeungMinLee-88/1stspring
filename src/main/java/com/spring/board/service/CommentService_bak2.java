package com.spring.board.service;

import com.spring.board.dto.CommentDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.CommentEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService_bak2 {
  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;

  /*public Long save(CommentDTO commentDTO) {
    *//* 부모엔티티(BoardEntity) 조회 *//*
    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
    if (optionalBoardEntity.isPresent()) {
      BoardEntity boardEntity = optionalBoardEntity.get();
      CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity);
      return commentRepository.save(commentEntity).getId();
    } else {
      return null;
    }
  }

  public List<CommentDTO> findAll(Long boardId) {
    BoardEntity boardEntity = boardRepository.findById(boardId).get();
    List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
    *//* EntityList -> DTOList *//*
    List<CommentDTO> commentDTOList = new ArrayList<>();
    for (CommentEntity commentEntity: commentEntityList) {
      CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
      commentDTOList.add(commentDTO);
    }
    return commentDTOList;
  }*/

}
