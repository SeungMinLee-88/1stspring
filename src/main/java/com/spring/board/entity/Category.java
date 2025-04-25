package com.spring.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long categoryId;

    @Column(nullable = false)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    @JsonIgnore
    public Category parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "root_category_id")
    @JsonIgnore
    public Category rootCategory;

    @Transient
    public List<Category> childrens = new ArrayList<Category>();
}
