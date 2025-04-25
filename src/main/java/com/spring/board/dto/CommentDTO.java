package com.spring.board.dto;

import com.spring.board.entity.BaseEntity;
import com.spring.board.entity.CommentEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO extends BaseEntity {
  private Long id;
  private String commentWriter;
  private String commentContents;
  private Long boardId;
  private List<CommentDTO> childrencomments;
  /*private Set<CommentDTO> commentDTOList;*/

/*  public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setId(commentEntity.getId());
    commentDTO.setCommentWriter(commentEntity.getCommentWriter());
    commentDTO.setCommentContents(commentEntity.getCommentContents());
    commentDTO.setBoardId(boardId);
    return commentDTO;
  }*/
}