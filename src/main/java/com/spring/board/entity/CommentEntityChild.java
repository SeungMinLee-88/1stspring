package com.spring.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntityChild extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 20, nullable = false)
  private String commentWriter;

  @Column
  private String commentContents;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private CommentEntity commentEntity;


/*  public static List<CommentEntity> tocommentList(CommentDTO commentDTO, BoardEntity boardEntity) {
    List<CommentEntity> commentEntity = new CommentEntity();
    commentEntity.setCommentWriter(commentDTO.getCommentWriter());
    commentEntity.setCommentContents(commentDTO.getCommentContents());
    commentEntity.setBoardEntity(boardEntity);
    return commentEntity;
  }*/
}
