package com.mka.employeeProject.dao;

import com.mka.employeeProject.entity.EmployeeBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeBenefitRepository extends JpaRepository<EmployeeBenefit, Long> {
  // params: entity type and primary key
}
