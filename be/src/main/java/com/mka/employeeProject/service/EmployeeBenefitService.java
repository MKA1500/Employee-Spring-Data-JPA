package com.mka.employeeProject.service;

import com.mka.employeeProject.entity.EmployeeBenefit;

import java.util.List;
import java.util.Optional;

public interface EmployeeBenefitService {
  List<EmployeeBenefit> findAll();
  Optional<EmployeeBenefit> findById(Long id);
  EmployeeBenefit save(EmployeeBenefit employeeBenefit);
  void deleteById(Long id);
}
