package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.BoardPostResponse;
import com.spring.board.dto.CommentDTO;
import com.spring.board.service.BoardService;
import com.spring.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class RestBoardController {
    private final BoardService boardService;
    private final CommentService commentService;


    @PutMapping("/save")
    public ResponseEntity<BoardPostResponse> boardSave(@ModelAttribute BoardDTO boardDTO) throws IOException {
        System.out.println("boardDTO = " + boardDTO);
        LocalDateTime time = LocalDateTime.now();
        System.out.println("time = " + time);

        boardService.save(boardDTO);

        //return boardService.save(boardDTO);
        return ResponseEntity.ok(BoardPostResponse
                .builder()
                .resultMessage("save success")
                .resultCode("200")
                .build());
    }

    @GetMapping("/list")
    public List<BoardDTO> findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        System.out.println("boardDTOList : " + boardDTOList.toString());
        //model.addAttribute("boardList", boardDTOList);
        return boardDTOList;
    }

/*    @GetMapping("/boardlist")
    public List<BoardDTO> boardList(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findList();
        System.out.println("boardDTOList : " + boardDTOList.toString());
        //model.addAttribute("boardList", boardDTOList);
        return boardDTOList;
    }*/

    @GetMapping("/{id}")
    public BoardDTO findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return boardService.findById(id);
    }

    @GetMapping("/update/{id}")
    public BoardDTO updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return boardService.findById(id);
    }

    @PostMapping("/update")
    public ResponseEntity<BoardPostResponse> boardUpdate(@ModelAttribute BoardDTO boardDTO, Model model,
                         @PageableDefault(page=1) Pageable pageable) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);

        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(board.getId());
        model.addAttribute("commentList", commentDTOList);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return ResponseEntity.ok(BoardPostResponse
                .builder()
                .resultMessage("update success")
                .resultCode("200")
                .build());
//        return "redirect:/board/" + boardDTO.getId();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> boardDelete(@PathVariable Long id) {

        try {
            boardService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body("delete success");
    }

    /*@PostMapping("/pagingList")
    public Page<BoardDTO> paging(@PageableDefault(page = 1) Pageable pageable, Model model, @RequestBody RequestParameters requestParameters){
        System.out.println("call pagingList");
        //pageable.getPageNumber();
        *//*
{
  "sortfield": "board_title",
  "searchfield": "board_title",
  "searchtext": "11"
}
        *//*
        System.out.println("reuestparam : " + requestParameters.getSortfield());
        String sortfield ="";
        String searchfield ="";
        String searchtext ="";

        Page<BoardDTO> boardList = boardService.paging(pageable, requestParameters);
        int blockLimit = 3;

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
*//*        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);*//*
        return boardList;
    }*/


    @GetMapping("/pagingList")
    public Page<BoardDTO> paging(@PageableDefault(page = 1) Pageable pageable, Model model, @RequestParam Map<String,String> params){

        Page<BoardDTO> boardList = boardService.paging(pageable, params);
        int blockLimit = 3;

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
/*        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);*/
        return boardList;
    }

}










