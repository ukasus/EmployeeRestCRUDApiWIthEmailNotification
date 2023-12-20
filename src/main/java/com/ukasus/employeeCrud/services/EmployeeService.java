package com.ukasus.employeeCrud.services;

import com.ukasus.employeeCrud.entities.Employee;
import com.ukasus.employeeCrud.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmailService emailService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmailService emailService) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    public Employee addEmployee(Employee employee) {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(UUID.randomUUID().toString()); // Generate unique UUID if not set
        }
        employee = employeeRepository.save(employee);
        try {
            Employee manager = (Employee) getNthManagerNameForEmployeeById(employee.getReportsTo(), 1);
            String emailContent = employee.getEmployeeName()+" will now work under you. Mobile number is "+employee.getPhoneNumber()+" and email is "+ employee.getEmail();
            emailService.sendEmail(manager.getEmail(),"New Employee Onboarded",emailContent);
        } catch (Exception e) {
            System.out.println("Email send operation to manager failed, hence only saving the employee details:");
            e.printStackTrace();
        }

        return employee; // Save the employee record
    }

    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateEmployee(String id, Employee updatedEmployeeDetails) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setEmployeeName(updatedEmployeeDetails.getEmployeeName());
            employee.setPhoneNumber(updatedEmployeeDetails.getPhoneNumber());
            employee.setEmail(updatedEmployeeDetails.getEmail());
            employee.setReportsTo(updatedEmployeeDetails.getReportsTo());
            employee.setProfileImage(updatedEmployeeDetails.getProfileImage());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee with ID: " + id + " not found."));
    }

    public Object getNthManagerNameForEmployeeById(String id, int level) {
        int n = 0;
        try{
            String nThManagerId = id;
            while (n++ < level) {
                nThManagerId = employeeRepository.findById(nThManagerId).get().getReportsTo();
            }
            Employee manager = employeeRepository.findById(nThManagerId).get();
            return manager;
        } catch (Exception e) {
            return e;
        }
    }
}
