package com.spring.board.service.BoardService;

import com.spring.board.dto.BoardDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  public void save(BoardDTO boardDTO) throws IOException {

      // 첨부 파일 없음.
      BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
      boardRepository.save(boardEntity);
  }

  @Transactional
  public List<BoardDTO> findAll() {
    List<BoardEntity> boardEntityList = boardRepository.findAll();
    List<BoardDTO> boardDTOList = new ArrayList<>();
    for (BoardEntity boardEntity: boardEntityList) {
      boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
    }
    return boardDTOList;
  }
}
