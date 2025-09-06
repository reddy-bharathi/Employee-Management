package com.excelr.repository;

import com.excelr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Additional query methods (if needed) go here
}
