package com.spring.board.entity;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20, nullable = false)
  private String commentWriter;

  @Column
  private String commentContents;

  /* Board:Comment = 1:N */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id")
  private BoardEntity boardEntity;

  @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CommentEntity> commentEntityList = new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "parent_id")
  private CommentEntity commentEntity;



  public static CommentEntity toSaveEntity(CommentDTO commentDTO, BoardEntity boardEntity) {
    CommentEntity commentEntity = new CommentEntity();
    commentEntity.setCommentWriter(commentDTO.getCommentWriter());
    commentEntity.setCommentContents(commentDTO.getCommentContents());
    commentEntity.setBoardEntity(boardEntity);
    return commentEntity;
  }

/*  public static List<CommentEntity> tocommentList(CommentDTO commentDTO, BoardEntity boardEntity) {
    List<CommentEntity> commentEntity = new CommentEntity();
    commentEntity.setCommentWriter(commentDTO.getCommentWriter());
    commentEntity.setCommentContents(commentDTO.getCommentContents());
    commentEntity.setBoardEntity(boardEntity);
    return commentEntity;
  }*/
}
