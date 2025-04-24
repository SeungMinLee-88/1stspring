package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.CommentDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.CommentService_bak2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService_bak2 commentServiceBak2;

   /* @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        System.out.println("boardDTOList : " + boardDTOList.toString());
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        *//*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         *//*
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.boardDetail(id);
        System.out.println("Board boardDTO : " + boardDTO);
        *//* 댓글 목록 가져오기 *//*
        List<CommentDTO> commentDTOList = commentServiceBak2.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.boardDetail(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model,
                         @PageableDefault(page=1) Pageable pageable) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);

        *//* 댓글 목록 가져오기 *//*
        List<CommentDTO> commentDTOList = commentServiceBak2.findAll(board.getId());
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        //pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();


        System.out.println("(((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) : " + (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1));
        System.out.println("pageable.getPageNumber() : " + pageable.getPageNumber());
        System.out.println("boardList.getTotalPages() : " + boardList.getTotalPages());
        System.out.println("pageable.getPageSize() : " + pageable.getPageSize());
        System.out.println("startPage : " + startPage);
        System.out.println("endPage : " + endPage);

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }*/

}