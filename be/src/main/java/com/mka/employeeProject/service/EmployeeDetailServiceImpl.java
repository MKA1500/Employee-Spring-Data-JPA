package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeDetailRepository;
import com.mka.employeeProject.entity.EmployeeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

  @Override
  public Optional<EmployeeDetail> findById(Long id) {
    return employeeDetailRepository.findById(id);
  }

  @Override
  public EmployeeDetail save(EmployeeDetail employeeDetail) {
    return employeeDetailRepository.save(employeeDetail);
  }

  @Override
  public void deleteById(Long id) {
    employeeDetailRepository.deleteById(id);
  }
}
