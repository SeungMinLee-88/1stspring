package com.spring.board.repository;

import com.spring.board.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Boolean existsByUsername(String username);

  UserEntity findByUsername(String username);
}
