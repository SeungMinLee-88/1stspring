package com.spring.board.service;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.BoardFileDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.BoardFileEntity;
import com.spring.board.entity.FileEntity;
import com.spring.board.entity.SearchCriteria;
import com.spring.board.repository.BoardFileRepository;
import com.spring.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BoardService {

  BoardDTO boardSaveAtta(BoardDTO boardDTO) throws IOException ;

  List<BoardDTO> findAll();

  void updateHits(Long id);

  BoardDTO boardDetail(Long id);

  List<BoardFileDTO> fileList(Long boardId);

  List<BoardFileDTO> fileDelete(Long fileId, Long boardId);


  Resource fetchFileAsResource(String fileName) throws FileNotFoundException ;

  void updateBoard(BoardDTO boardDTO) throws IOException ;

  void boardDelete(Long id);


  Page<BoardDTO> pagingList(Pageable pageable, Map<String, String> params);


  BoardDTO save(BoardDTO boardDTO) throws IOException ;

  BoardDTO update(BoardDTO boardDTO);

  Page<BoardDTO> paging(Pageable pageable);

}
