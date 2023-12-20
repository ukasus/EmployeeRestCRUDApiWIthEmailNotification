package com.ukasus.employeeCrud.controllers;

import com.ukasus.employeeCrud.entities.Employee;
import com.ukasus.employeeCrud.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.addEmployee(employee);
        if (savedEmployee != null) {
            return ResponseEntity.ok(savedEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @PageableDefault(sort = "employeeName", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Employee> page = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable String id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            // Handle the case where the employee does not exist or cannot be deleted
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID: " + id + " not found.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(
            @PathVariable String id, @Valid @RequestBody Employee employeeDetails) {
        try {
            employeeDetails.setId(id);
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/manager/{id}/{level}")
    public ResponseEntity<Object> getNthManagerNameForEmployeeById(@PathVariable String id, @PathVariable() int level) {
        Object nThManager = employeeService.getNthManagerNameForEmployeeById(id,level);
        return ResponseEntity.ok(nThManager);
    }

}
