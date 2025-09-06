package com.excelr.service;

import com.excelr.dto.EmployeeDto;
import com.excelr.entity.Employee;
import com.excelr.exception.EmployeeNotFoundException;
import com.excelr.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    // Create
    public EmployeeDto create(EmployeeDto dto) {
        Employee e = new Employee(dto.getName(), dto.getDepartment(), dto.getSalary());
        Employee saved = repo.save(e);
        return toDto(saved);
    }

    // Read all
    public List<EmployeeDto> getAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // Read by id
    public EmployeeDto getById(Long id) {
        Employee e = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return toDto(e);
    }

    // Update
    public EmployeeDto update(Long id, EmployeeDto dto) {
        Employee existing = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDepartment() != null) existing.setDepartment(dto.getDepartment());
        if (dto.getSalary() != null) existing.setSalary(dto.getSalary());
        Employee updated = repo.save(existing);
        return toDto(updated);
    }

    // Delete
    public void delete(Long id) {
        Employee existing = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        repo.delete(existing);
    }

    // Helper
    private EmployeeDto toDto(Employee e) {
        return new EmployeeDto(e.getId(), e.getName(), e.getDepartment(), e.getSalary());
    }
}
