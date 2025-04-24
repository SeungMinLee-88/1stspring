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

public interface CommentService {
  List<CommentDTO> commentList(Long boardId);

}
