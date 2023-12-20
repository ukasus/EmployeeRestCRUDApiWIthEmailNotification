package com.ukasus.employeeCrud.entities;

import com.ukasus.employeeCrud.validation.annotations.EmployeeReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data // Generates getters, setters, equals, hashCode and toString methods
@AllArgsConstructor // Generates a constructor with all arguments
@Document
public class Employee {
    @Id
    private String id;
    @NotEmpty
    private String employeeName;
    private String phoneNumber;
    @Email
    private String email;
    @EmployeeReference
    private String reportsTo;
    private String profileImage;

    public Employee() {
        this.id = UUID.randomUUID().toString(); // Generate a unique UUID
    }
}