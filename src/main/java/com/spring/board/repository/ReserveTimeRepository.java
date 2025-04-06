package com.spring.board.repository;

import com.spring.board.entity.ReserveEntity;
import com.spring.board.entity.ReserveTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveTimeRepository extends JpaRepository<ReserveTimeEntity, Long> {
}
