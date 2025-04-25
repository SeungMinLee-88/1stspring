package com.spring.board.controller;

import com.spring.board.dto.*;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.Category;
import com.spring.board.entity.CommentEntity;
import com.spring.board.repository.BoardRepository;
import com.spring.board.repository.CategoryRepository;
import com.spring.board.repository.CommentRepository;
import com.spring.board.service.BoardService;
import com.spring.board.service.CommentService;
import com.spring.board.service.CommentService_bak2;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class RestCommentController {


    private final BoardService boardService;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/commentList")
    public List<CommentDTO> commentList(@RequestParam Long boardId) {
        System.out.println("boardId : " + boardId);
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<CommentDTO> commentDTOList = commentService.commentList(boardId);
        System.out.println("commentDTOList : " + commentDTOList.toString());
        //model.addAttribute("boardList", boardDTOList);
        return commentDTOList;
    }

    @GetMapping("/commentTrees")
    @Transactional(readOnly = true)
    public Page<CommentEntity> commentTrees(@PageableDefault(page = 1) Pageable pageable, @RequestParam Long boardId) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3;

        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        /*Page<CommentEntity> rootCommentEntity = commentRepository.findAllRoots(boardId, PageRequest.of(page, pageable.getPageSize())); // first db call*/
        Page<CommentEntity> rootCommentEntity = commentRepository.findByBoardEntityAndParentCommentEntityIsNull(boardEntity, PageRequest.of(page, pageable.getPageSize()));
        ModelMapper mapper = new ModelMapper();

        Page<CommentDTO> rootCommentDTOList = mapper.map(rootCommentEntity, new TypeToken<Page<CommentDTO>>() {
        }.getType());
        System.out.println("aaaaaaaaaa  rootCategories : " + rootCommentDTOList.toString());

        // Now Find all the subcategories
        List<Long> rootCommentIds = rootCommentEntity.stream().map(CommentEntity::getId).collect(Collectors.toList());
        List<CommentEntity> subComments = commentRepository.findAllSubCommentEntitysInRoot(rootCommentIds); // second db call

        subComments.forEach(subComment -> {
            //subComment.getParentCommentEntity().getChildrencomments().add(subComment); // no further db call, because everyone inside the root is in the persistence context.
        });

        return rootCommentEntity;
    }


    @GetMapping("/categories")
    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        ModelMapper mapper = new ModelMapper();
        List<Category> rootCategories = categoryRepository.findAllRoots(); // first db call

        List<CategoryDTO> rootcategoryDTOList = mapper.map(rootCategories, new TypeToken<List<CategoryDTO>>() {
        }.getType());
        System.out.println("aaaaaaaaaa  rootCategories : " + rootcategoryDTOList);


        // Now Find all the subcategories
        List<Long> rootCategoryIds = rootCategories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        System.out.println("rootCategoryIds : " + rootCategoryIds);
        List<Category> subCategories = categoryRepository.findAllSubCategoriesInRoot(rootCategoryIds); // second db call


        List<CategoryDTO> categoryDTOList = mapper.map(subCategories, new TypeToken<List<CategoryDTO>>() {
        }.getType());
        System.out.println("subCategories : " + categoryDTOList);

        subCategories.forEach(subCategory -> {

            CategoryDTO categoryDTO = mapper.map(subCategory, new TypeToken<CategoryDTO>() {
            }.getType());
            System.out.println("aaaaaaaaa categoryDTO : " + categoryDTO);
            subCategory.getParentCategory().getChildrens().add(subCategory); // no further db call, because everyone inside the root is in the persistence context.

            CategoryDTO categoryDTO2 = mapper.map(subCategory, new TypeToken<CategoryDTO>() {
            }.getType());
            System.out.println("bbbbbbbbb categoryDTO : " + categoryDTO2);
        });

        List<CategoryDTO> rootcategoryDTOList2 = mapper.map(rootCategories, new TypeToken<List<CategoryDTO>>() {
        }.getType());
        System.out.println("bbbbbbbbbb  rootCategories : " + rootcategoryDTOList2);
        return rootCategories;
    }

   /* @GetMapping("/commentListTest")
    public ResponseEntity<Set<CommentDTO>> commentListTest(@RequestParam Long boardId) {

        BoardEntity boardEntity = boardRepository.findById(31L).get();
        Long commentId = 1L;
        Optional<CommentEntity> commentEntityList = commentRepository.findById(boardId);
        ModelMapper mapper = new ModelMapper();
        CommentEntity commentEntities = commentEntityList.get();
        CommentDTO commentDTOList = mapper.map(commentEntities, new TypeToken<CommentDTO>() {
        }.getType());
        System.out.println("commentDTOList : " + commentDTOList.toString());


        return commentRepository.findById(boardId).map(findSiblings).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
                *//*.orElse(ResponseEntity.notFound().build());*//*
    }

    private Function<CommentEntity, Set<CommentDTO>> findSiblings = comment -> comment.getCommentEntity().getCommentEntityList().stream().map(p -> CommentDTO.builder().id(p.getId()).id(p.getId()).build()).collect(Collectors.toSet());*/


}










