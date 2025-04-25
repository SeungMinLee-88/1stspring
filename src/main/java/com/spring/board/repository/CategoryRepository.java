package com.spring.board.repository;

import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.Category;
import com.spring.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT category FROM Category category "
          + " WHERE category.parentCategory.categoryId IS NULL")
  public List<Category> findAllRoots();

  @Query("SELECT category FROM Category category"
          + " WHERE category.rootCategory.categoryId IN :rootIds ")
  public List<Category> findAllSubCategoriesInRoot(@Param("rootIds") List<Long> rootIds);
}