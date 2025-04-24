package com.spring.board.service.impl;

import com.spring.board.dto.BoardFileDTO;
import com.spring.board.dto.CommentDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.CommentEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.CommentRepository;
import com.spring.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class CommentServiceImpl implements CommentService {


  CommentRepository commentRepository;
  BoardRepository boardRepository;

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository, BoardRepository boardRepository){
    this.commentRepository = commentRepository;
    this.boardRepository = boardRepository;
  }

  @Override
  public List<CommentDTO> commentList(Long boardId) {
    BoardEntity boardEntity = boardRepository.findById(boardId).get();
    List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityAndCommentEntityIsNull(boardEntity);

    ModelMapper mapper = new ModelMapper();
    List<CommentDTO> commentDTOList = mapper.map(commentEntityList, new TypeToken<List<CommentDTO>>() {
    }.getType());

    return commentDTOList;
  }


}
