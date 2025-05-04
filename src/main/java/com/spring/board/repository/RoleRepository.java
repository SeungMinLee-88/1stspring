package com.spring.board.repository;

import com.spring.board.entity.RoleEntity;
import com.spring.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
