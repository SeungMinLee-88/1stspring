package com.spring.board.repository;

import com.spring.board.dto.ReserveDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {

    @Query(value ="select b.* from reserve b where reserve_date = :reserveDate"
            , countQuery = "select count(*) from reserve b where reserve_date = :reserveDate\""
            ,nativeQuery = true)
    List<ReserveEntity> findByreserveDateQuery(String reserveDate);

    List<ReserveEntity> findByReserveDateContainingAndReserveUserIdContaining(String reserveDate, String reserveUserId);
}
