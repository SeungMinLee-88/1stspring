package com.spring.board.repository;

import com.spring.board.dto.ReserveTimeDTO;
import com.spring.board.dto.TimeDto;
import com.spring.board.entity.ReserveEntity;
import com.spring.board.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {
}
