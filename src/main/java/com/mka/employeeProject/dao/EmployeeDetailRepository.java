package com.mka.employeeProject.dao;

import com.mka.employeeProject.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetail, Long> {
  // params: entity type and primary key
}
