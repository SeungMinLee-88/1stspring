package com.spring.board.controller;

import com.spring.board.dto.EmployeeDto;
import com.spring.board.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    public EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EmployeeDto>> test(@RequestParam Map<String,String> params) {
        Page<EmployeeDto> employeeDto = this.employeeService.searchEmployeeWithPagination(params);
        return ResponseEntity.ok(employeeDto);
    }
}
