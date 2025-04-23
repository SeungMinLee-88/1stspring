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

@Service
@RequiredArgsConstructor
public class BoardService {
  private final BoardRepository boardRepository;
  private final BoardFileRepository boardFileRepository;



  public BoardDTO boardSaveAtta(BoardDTO boardDTO) throws IOException {
    // 파일 첨부 여부에 따라 로직 분리
    ModelMapper mapper = new ModelMapper();
    if (boardDTO.getFileList().length == 0) {
    System.out.println("boardSaveAtta boardDTO = " + boardDTO);
    boardDTO.setFileAttached(0);
    BoardEntity saveBoardEntity = BoardEntity.toSaveEntity(boardDTO);
    BoardEntity boardEntitys = boardRepository.save(saveBoardEntity);

    System.out.println("boardEntitys : " + boardEntitys.toString());
    BoardDTO boardDTO1  = mapper.map(boardEntitys, new TypeToken<BoardDTO>(){}.getType());
    return boardDTO1;
        } else {
    // 첨부 파일 있음.
      boardDTO.setFileAttached(1);
      BoardEntity saveBoardEntity = BoardEntity.toSaveEntity(boardDTO);
      BoardEntity boardEntitys = boardRepository.save(saveBoardEntity);
      Long savedId = boardRepository.save(saveBoardEntity).getId();
      BoardEntity board = boardRepository.findById(savedId).get();


      if(boardDTO.getFileList().length > 0) {
        for (MultipartFile boardFile : boardDTO.getFileList()) {
          //MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
          String originalFilename = boardFile.getOriginalFilename(); // 2.
          System.out.println("originalFilename : " + originalFilename);
          String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
          String savePath = "C:/Users/lsmls/IdeaProjects/1stspring/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
          boardFile.transferTo(new File(savePath)); // 5.
          BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
          boardFileRepository.save(boardFileEntity);
        }
      }

      System.out.println("boardEntitys : " + boardEntitys.toString());
      BoardDTO boardDTO1  = mapper.map(boardEntitys, new TypeToken<BoardDTO>(){}.getType());
      return boardDTO1;
      }

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

  @Transactional
  public void updateHits(Long id) {
    boardRepository.updateHits(id);
  }

  @Transactional
  public BoardDTO boardDetail(Long id) {
    Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
    if (optionalBoardEntity.isPresent()) {
      BoardEntity boardEntity = optionalBoardEntity.get();

      BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);

      System.out.println("boardDTO.getFileAttached() : " + boardDTO.getFileAttached());

      if (boardDTO.getFileAttached() == 1) {
        ModelMapper mapper = new ModelMapper();
        List<BoardFileDTO> boardFileDTOList = mapper.map(boardEntity.getBoardFileEntityList(), new TypeToken<List<BoardFileDTO>>() {
        }.getType());

        boardDTO.setBoardFileDTO(boardFileDTOList);
      }
      System.out.println("boardDetail boardDTO : " + boardDTO);

/*      List<BoardFileEntity> boardFileEntityList = boardFileRepository.findByBoardId(boardEntity.getId());

      ModelMapper mapper = new ModelMapper();
      List<BoardFileDTO> boardFileDTOList = mapper.map(boardFileEntityList, new TypeToken<List<BoardFileDTO>>(){}.getType());

      boardDTO.setBoardFileDTO(boardFileDTOList);*/

      return boardDTO;
    } else {
      return null;
    }
  }

  @Transactional
  public List<BoardFileDTO> fileList(Long boardId) {
    List<BoardFileEntity> boardFileEntityList = boardFileRepository.findByBoardId(boardId);

        ModelMapper mapper = new ModelMapper();
        List<BoardFileDTO> fileDTOList = mapper.map(boardFileEntityList, new TypeToken<List<BoardFileDTO>>() {
        }.getType());

      System.out.println("fileList fileDTOList : " + fileDTOList);
      return fileDTOList;
  }


  public Resource fetchFileAsResource(String fileName) throws FileNotFoundException {
    Path UPLOAD_PATH;
    try {
        try {
            /*UPLOAD_PATH = Paths.get(new ClassPathResource("").getFile().getAbsolutePath() + File.separator + "static"  + File.separator + "image");*/
          UPLOAD_PATH = Paths.get("C:\\Users\\lsmls\\IdeaProjects\\1stspring\\springboot_img");
          System.out.println("new ClassPathResource(\"\").getFile().getAbsolutePath() "  + Paths.get(new ClassPathResource("").getFile().getAbsolutePath()).toString());
            System.out.println("UPLOAD_PATH "  + UPLOAD_PATH.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Path filePath = UPLOAD_PATH.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new FileNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new FileNotFoundException("File not found " + fileName);
    }
  }
  public void updateBoard(BoardDTO boardDTO) throws IOException {
    // 파일 첨부 여부에 따라 로직 분리
    List<BoardFileEntity> boardFileEntityList = boardFileRepository.findByBoardId(boardDTO.getId());
    ModelMapper mapper = new ModelMapper();
    List<BoardFileDTO> fileDTOList = mapper.map(boardFileEntityList, new TypeToken<List<BoardFileDTO>>() {
    }.getType());

    System.out.println("fileList fileDTOList : " + fileDTOList);

    if (boardDTO.getFileList().length == 0 && boardFileEntityList.size() == 0) {
      System.out.println("attached XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
      boardDTO.setFileAttached(0);
      BoardEntity saveBoardEntity = BoardEntity.toSaveEntity(boardDTO);
      BoardEntity boardEntitys = boardRepository.save(saveBoardEntity);

      System.out.println("boardEntitys : " + boardEntitys.toString());
      BoardDTO boardDTO1  = mapper.map(boardEntitys, new TypeToken<BoardDTO>(){}.getType());
      /*return boardDTO1;*/
    } else {
      // 첨부 파일 있음.
      boardDTO.setFileAttached(1);
      BoardEntity saveBoardEntity = BoardEntity.toSaveEntity(boardDTO);
      BoardEntity boardEntitys = boardRepository.save(saveBoardEntity);
      Long savedId = boardRepository.save(saveBoardEntity).getId();
      BoardEntity board = boardRepository.findById(savedId).get();


      if(boardDTO.getFileList().length > 0 ||  boardFileEntityList.size() == 0) {
        System.out.println("attached OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        for (MultipartFile boardFile : boardDTO.getFileList()) {
          //MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
          String originalFilename = boardFile.getOriginalFilename(); // 2.
          System.out.println("originalFilename : " + originalFilename);
          String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
          String savePath = "C:/Users/lsmls/IdeaProjects/1stspring/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
          boardFile.transferTo(new File(savePath)); // 5.
          BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
          boardFileRepository.save(boardFileEntity);
        }
      }

      /*System.out.println("boardEntitys : " + boardEntitys.toString());
      BoardDTO boardDTO1  = mapper.map(boardEntitys, new TypeToken<BoardDTO>(){}.getType());
      return boardDTO1;*/
    }
  }

  public void delete(Long id) {
    boardRepository.deleteById(id);
  }

/*  public List<BoardDTO> findList(){
    List<BoardEntity> boardEntities = boardRepository.findAllboards123123();

    ModelMapper mapper = new ModelMapper();

    List<BoardDTO> boardDTOList = mapper.map(boardEntities, new TypeToken<List<BoardDTO>>(){}.getType());

    return boardDTOList;

  }*/


  public Page<BoardDTO> pagingList(Pageable pageable, Map<String, String> params){
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;

    System.out.println("pageable : " + pageable.getPageSize());

    System.out.println("params : " + params.toString());

    /*if(params.get("searchKey") != null){
      Specification<BoardEntity> specification = new BoardSpecification(new SearchCriteria(params.get("searchKey"), params.get("searchValue")));
      Page<BoardEntity> boardEntities = boardRepository.findAll(specification, PageRequest.of(page, pageable.getPageSize(), pageable.getSort()));
    }
    else{
      Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageable.getPageSize(), pageable.getSort()));
    }*/


    Specification<BoardEntity> specification = new BoardSpecification(new SearchCriteria(params.get("searchKey"), params.get("searchValue")));

    /*Page<BoardEntity> boardEntities = boardRepository.findAll(specification, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));*/

    Sort sort = pageable.getSort();

    Sort.Direction direction;

    Page<BoardEntity> boardEntities = boardRepository.findAll(specification, PageRequest.of(page, pageable.getPageSize(), pageable.getSort()));

  /*  System.out.println("boardEntities.getContent() = " + boardEntities.getContent());
    System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
    System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
    System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
    System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
    System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
    System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
    System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부*/

    Page<BoardDTO> boardDTOList = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

    return boardDTOList;

  }


  public BoardDTO save(BoardDTO boardDTO) throws IOException {
    // 파일 첨부 여부에 따라 로직 분리
    /*if (boardDTO.getBoardFile().isEmpty()) {*/

    BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
    BoardEntity boardEntitys = boardRepository.save(boardEntity);
    /*    } else {*/
    // 첨부 파일 있음.
/*      BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
      Long savedId = boardRepository.save(boardEntity).getId();
      BoardEntity board = boardRepository.findById(savedId).get();

      for(MultipartFile boardFile : boardDTO.getBoardFile()) {
        //MultipartFile boardFile = boardDTO.getBoardFile(); // 1.
        String originalFilename = boardFile.getOriginalFilename(); // 2.
        String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
        String savePath = "C:/Users/user/IdeaProjects/board/springboot_img/" + storedFileName; // 4. C:/springboot_img/9802398403948_내사진.jpg
//            String savePath = "/Users/사용자이름/springboot_img/" + storedFileName; // C:/springboot_img/9802398403948_내사진.jpg
        boardFile.transferTo(new File(savePath)); // 5.
        BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
        boardFileRepository.save(boardFileEntity);
      }*/
    /*  }*/

    ModelMapper mapper = new ModelMapper();

    System.out.println("boardEntitys : " + boardEntitys.toString());
    BoardDTO boardDTO1  = mapper.map(boardEntitys, new TypeToken<BoardDTO>(){}.getType());
    return boardDTO1;
  }




  public BoardDTO update(BoardDTO boardDTO) {
    BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
    boardRepository.save(boardEntity);
    return boardDetail(boardDTO.getId());
  }

  public Page<BoardDTO> paging(Pageable pageable){
    int page = pageable.getPageNumber() - 1;
    int pageLimit = 3;
    Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
    System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
    System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
    System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
    System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
    System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
    System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
    System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
    System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

    Page<BoardDTO> boardDTOList = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));

    return boardDTOList;

  }

}
