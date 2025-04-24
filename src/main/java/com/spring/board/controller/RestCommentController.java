package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.BoardFileDTO;
import com.spring.board.dto.BoardPostResponse;
import com.spring.board.dto.CommentDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.CommentService;
import com.spring.board.service.CommentService_bak2;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class RestCommentController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping("/commentList")
    public List<CommentDTO> commentList(@RequestParam Long boardId) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<CommentDTO> commentDTOList = commentService.commentList(boardId);
        System.out.println("commentDTOList : " + commentDTOList.toString());
        //model.addAttribute("boardList", boardDTOList);
        return commentDTOList;
    }


}










