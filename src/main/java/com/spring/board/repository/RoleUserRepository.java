package com.spring.board.repository;

import com.spring.board.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<RoleUserEntity, Long> {
}
