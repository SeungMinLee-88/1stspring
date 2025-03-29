package com.spring.board.service;

import com.spring.board.entity.Employee;
import com.spring.board.entity.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate
            (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

/*        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {*/
        System.out.println("criteria.getSearchKey()).getJavaType() : " + root.get(criteria.getSearchKey()).getJavaType());
        if (root.get(criteria.getSearchKey()).getJavaType() == String.class) {
            System.out.println("if 111111111111111111");
               return builder.like(
               root.<String>get(criteria.getSearchKey()), "%" + criteria.getSearchValue() + "%");
           } else {
            System.out.println("if 2222222222222222222");
              return builder.equal(root.get(criteria.getSearchKey()), criteria.getSearchValue());
        }
/*        }*/
        //return null;
    }

}
