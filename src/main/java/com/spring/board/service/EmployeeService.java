package com.spring.board.service;

import com.spring.board.dto.BoardDTO;
import com.spring.board.dto.EmployeeDto;
import com.spring.board.entity.Employee;
import com.spring.board.entity.SearchCriteria;
import com.spring.board.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Page<EmployeeDto> searchEmployeeWithPagination(Map<String, String> params) {

        System.out.println("params : " + params.toString());
        int offset = Integer.parseInt(params.get("offset"));
        int pageSize = Integer.parseInt(params.get("pageSize"));
        PageRequest pageable = PageRequest.of(offset,pageSize);

        params.remove("offset");
        params.remove("pageSize");

        /*Specification<Employee> specification = EmployeeSpecification.getSpecification(params);*/

        Specification<Employee> specification = new EmployeeSpecification(new SearchCriteria(params.get("searchkey"), params.get("searchvalue")));

        final Page<Employee> employees = this.employeeRepository.findAll(specification,pageable);

        System.out.println("employeesentities : " + employees.getTotalElements());

        Page<EmployeeDto> employeeDtos = employees.map(employee -> new EmployeeDto(employee.getId(), employee.getFirstname(), employee.getLastname(), employee.getEmail()));


        System.out.println("EmployeeDto : " + employeeDtos.getTotalElements());
        return employeeDtos;
    }
}
