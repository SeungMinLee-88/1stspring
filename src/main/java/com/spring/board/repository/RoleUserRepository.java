package com.spring.board.repository;

import com.spring.board.entity.RoleUserEntity;
import com.spring.board.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleUserRepository extends JpaRepository<RoleUserEntity, Long> {

    @Query(value ="SELECT r.* FROM role_user r where user_id = :loginId"
            , countQuery = "select count(*) FROM role_user r where user_id = :loginId"
            ,nativeQuery = true)
    List<RoleUserEntity> findByLoginId(Long loginId);
}
