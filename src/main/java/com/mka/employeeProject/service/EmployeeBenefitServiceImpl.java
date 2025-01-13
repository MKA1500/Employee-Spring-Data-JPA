package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeBenefitRepository;
import com.mka.employeeProject.entity.EmployeeBenefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeBenefitServiceImpl implements EmployeeBenefitService {

  private final EmployeeBenefitRepository employeeBenefitRepository;

  @Autowired
  public EmployeeBenefitServiceImpl(EmployeeBenefitRepository employeeBenefitRepository) {
    this.employeeBenefitRepository = employeeBenefitRepository;
  }

  @Override
  public List<EmployeeBenefit> findAll() {
    return employeeBenefitRepository.findAll();
  }

  @Override
  public Optional<EmployeeBenefit> findById(Long id) {
    return employeeBenefitRepository.findById(id);
  }

  @Override
  public EmployeeBenefit save(EmployeeBenefit employeeBenefit) {
    return employeeBenefitRepository.save(employeeBenefit);
  }

  @Override
  public void deleteById(Long id) {
    employeeBenefitRepository.deleteById(id);
  }
}
