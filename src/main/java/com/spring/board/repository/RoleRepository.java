package com.spring.board.repository;

import com.spring.board.entity.RoleEntity;
import com.spring.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByIdNotIn(List<Long> roleIds);
}
