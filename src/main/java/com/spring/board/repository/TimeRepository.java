package com.spring.board.repository;

import com.spring.board.dto.ReserveTimeDTO;
import com.spring.board.dto.TimeDto;
import com.spring.board.entity.ReserveEntity;
import com.spring.board.entity.ReserveTimeEntity;
import com.spring.board.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TimeRepository extends JpaRepository<TimeEntity, Long> {

    @Query(value ="select time.id, time.time, nvl2(reserve_time.time_id, '1', '0') reserved " +
            ",(select user_name from reserve where id = reserve_time.reserve_id ) user_name " +
            "from time left outer join reserve_time " +
            "on time.id = reserve_time.time_id and reserve_time.reserve_date = :reserveDate"
            , countQuery = "select count(*) from time left outer join reserve_time " +
            "on time.id = reserve_time.time_id and reserve_time.reserve_date = :reserveDate"
            ,nativeQuery = true)
    List<TimeEntity> findByReserveDate(String reserveDate);
}
