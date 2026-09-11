package com.mka.employeeProject.rest;

import com.mka.employeeProject.entity.Employee;
import com.mka.employeeProject.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

  private final EmployeeService employeeService;

  public EmployeeRestController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping
  public ResponseEntity<List<Employee>> findAll() {
    List<Employee> employees = employeeService.findAll();
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/{employeeId}")
  public ResponseEntity<Employee> findById(@PathVariable Long employeeId) {
    return employeeService.findById(employeeId)
        .map(employee -> {
          if (employee.getEmployeeDetail() != null) {
            employee.getEmployeeDetail().getDepartment(); // Initialize lazy-loaded field
          }
          return ResponseEntity.ok(employee);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
    if (employee.getId() != null) {
      return ResponseEntity.badRequest().build(); // Avoid creating with an existing ID
    }

    if (employee.getEmployeeDetail() == null) {
      return ResponseEntity.badRequest().body(null); // EmployeeDetail is mandatory
    }

    Employee savedEmployee = employeeService.save(employee);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
  }

  @PutMapping("/{employeeId}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable Long employeeId, @RequestBody Employee updatedEmployee) {
    return employeeService.findById(employeeId)
        .map(existingEmployee -> {
          if (updatedEmployee.getEmployeeDetail() != null) {
            existingEmployee.setEmployeeDetail(updatedEmployee.getEmployeeDetail());
          }
          existingEmployee.setFirstName(updatedEmployee.getFirstName());
          existingEmployee.setLastName(updatedEmployee.getLastName());
          existingEmployee.setEmail(updatedEmployee.getEmail());
          existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());

          Employee savedEmployee = employeeService.save(existingEmployee);
          return ResponseEntity.ok(savedEmployee);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{employeeId}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
    if (employeeService.findById(employeeId).isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    employeeService.deleteById(employeeId);
    return ResponseEntity.noContent().build();
  }
}