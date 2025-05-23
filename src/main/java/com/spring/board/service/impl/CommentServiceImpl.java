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
import java.util.function.Function;
import java.util.stream.Collectors;

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
/*    BoardEntity boardEntity = boardRepository.findById(boardId).get();
    List<CommentEntity> commentEntityList = commentRepository.findByBoardEntity(boardEntity);


    ModelMapper mapper = new ModelMapper();
    List<CommentDTO> commentDTOList = mapper.map(commentEntityList, new TypeToken<List<CommentDTO>>() {
    }.getType());*/
    List<CommentDTO> commentDTOList = new CommentDTO().getChildrencomments();

    return commentDTOList;
  }

  @Override
  public void commentSave(CommentDTO commentDTO) {
/*    BoardEntity boardEntity = boardRepository.findById(boardId).get();
    List<CommentEntity> commentEntityList = commentRepository.findByBoardEntity(boardEntity);


    ModelMapper mapper = new ModelMapper();
    List<CommentDTO> commentDTOList = mapper.map(commentEntityList, new TypeToken<List<CommentDTO>>() {
    }.getType());*/
    BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).get();
    Optional<CommentEntity> optionalParentCommentEntity = commentRepository.findById(commentDTO.getParentCommentId());

    Optional<CommentEntity> optionalRootCommentEntity = commentRepository.findById(commentDTO.getRootCommentId());



    /*CommentEntity saveCommentEntity = CommentEntity.toSaveEntity(commentDTO, boardEntity, parentCommentEntity, rootCommentEntity);*/
    System.out.println("isRootComment : " + commentDTO.getIsRootComment());
    if(commentDTO.getIsRootComment().equals("true")){
      CommentEntity saveCommentEntity = new CommentEntity().builder()
              .commentWriter(commentDTO.getCommentWriter())
              .commentContents(commentDTO.getCommentContents())
              .boardEntity(boardEntity).build();
      commentRepository.save(saveCommentEntity);
    }else{
      CommentEntity saveCommentEntity = new CommentEntity().builder()
              .commentWriter(commentDTO.getCommentWriter())
              .commentContents(commentDTO.getCommentContents())
              .boardEntity(boardEntity)
              .parentCommentEntity(optionalParentCommentEntity.get())
              .rootCommentEntity(optionalRootCommentEntity.get()).build();
      commentRepository.save(saveCommentEntity);
    }

  }

  @Override
  public void commentUpdate(CommentDTO commentDTO) {
  }

}
