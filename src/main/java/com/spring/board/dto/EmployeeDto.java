package com.spring.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
@Builder
public class EmployeeDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
}
