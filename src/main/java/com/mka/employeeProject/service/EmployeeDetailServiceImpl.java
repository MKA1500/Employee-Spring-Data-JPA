package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeDetailRepository;
import com.mka.employeeProject.dao.EmployeeRepository;
import com.mka.employeeProject.entity.Employee;
import com.mka.employeeProject.entity.EmployeeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDetailServiceImpl implements EmployeeDetailService {

  private final EmployeeDetailRepository employeeDetailRepository;

  @Autowired
  public EmployeeDetailServiceImpl(EmployeeDetailRepository employeeDetailRepository) {
    this.employeeDetailRepository = employeeDetailRepository;
  }

  @Override
  public List<EmployeeDetail> findAll() {
    return employeeDetailRepository.findAll();
  }
}
