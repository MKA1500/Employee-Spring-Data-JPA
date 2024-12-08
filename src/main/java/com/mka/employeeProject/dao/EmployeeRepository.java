package com.mka.employeeProject.dao;

import com.mka.employeeProject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  // params: entity type and primary key
}
