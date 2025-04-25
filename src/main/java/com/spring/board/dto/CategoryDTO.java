package com.spring.board.dto;

import com.spring.board.entity.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {
    public Long categoryId;
    public String name;
    public List<CategoryDTO> parentCategory;
    public List<CategoryDTO> rootCategory;
    public List<CategoryDTO> childrens;
}
