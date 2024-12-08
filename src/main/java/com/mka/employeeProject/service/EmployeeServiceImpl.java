package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeRepository;
import com.mka.employeeProject.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
    this.employeeRepository = employeeRepo;
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> findById(Long id) {
    return employeeRepository.findById(id);
  }

  @Override
  public Employee save(Employee employee) {
    employeeRepository.save(employee);
    return employee;
  }

  @Override
  public void deleteById(Long id) {
    employeeRepository.deleteById(id);
  }
}
