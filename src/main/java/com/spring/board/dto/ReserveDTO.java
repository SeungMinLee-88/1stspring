package com.spring.board.dto;

import com.spring.board.entity.ReserveEntity;
import com.spring.board.entity.ReserveTimeEntity;
import lombok.*;
import org.hibernate.mapping.Map;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@Builder
public class ReserveDTO {
    private Long id;
    private String reserveReason;
    private String reserveDate;
    private String reservePeriod;
    private String username;
    private Long userId;

    private Long hallId;
    private List<Long> reserveTimeSave;
    private List<ReserveTimeDTO> reserveTime;
    private LocalDateTime reserveCreatedTime;
    private LocalDateTime reserveUpdatedTime;

}
