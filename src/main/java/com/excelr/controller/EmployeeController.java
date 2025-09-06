package com.excelr.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excelr.dto.EmployeeDto;
import com.excelr.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    // Constructor injection
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Create
    @PostMapping
    public ResponseEntity<Map<String, Object>> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        EmployeeDto created = service.create(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee successfully inserted");
        response.put("employee", created);

        return ResponseEntity
                .created(URI.create("/api/employees/" + created.getId()))
                .body(response);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Read by id
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Update (replace)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
                                                      @Valid @RequestBody EmployeeDto dto) {
        EmployeeDto updated = service.update(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee successfully updated");
        response.put("employee", updated);

        return ResponseEntity.ok(response);
    }

    // Partial update (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patch(@PathVariable Long id,
                                                     @RequestBody EmployeeDto dto) {
        EmployeeDto updated = service.update(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Employee successfully updated (patch)");
        response.put("employee", updated);

        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee successfully deleted");

        return ResponseEntity.ok(response);
    }
}
