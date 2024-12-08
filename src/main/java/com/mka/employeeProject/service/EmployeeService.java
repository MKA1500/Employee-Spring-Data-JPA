package com.mka.employeeProject.service;

import com.mka.employeeProject.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

  List<Employee> findAll();

  Optional<Employee> findById(Long id);

  Employee save(Employee employee);

  void deleteById(Long id);
}
