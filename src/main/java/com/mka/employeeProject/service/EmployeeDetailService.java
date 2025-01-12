package com.mka.employeeProject.service;

import com.mka.employeeProject.entity.EmployeeDetail;

import java.util.List;
import java.util.Optional;

public interface EmployeeDetailService {
  List<EmployeeDetail> findAll();

  Optional<EmployeeDetail> findById(Long id);

  EmployeeDetail save(EmployeeDetail employeeDetail);

  void deleteById(Long id);
}
