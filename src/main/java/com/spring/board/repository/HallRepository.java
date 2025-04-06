package com.spring.board.repository;

import com.spring.board.entity.HallEntity;
import com.spring.board.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<HallEntity, Long> {
}
