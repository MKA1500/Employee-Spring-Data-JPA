package com.mka.employeeProject.rest;

import com.mka.employeeProject.entity.EmployeeBenefit;
import com.mka.employeeProject.service.EmployeeBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employeeBenefits")
public class EmployeeBenefitRestController {

  private final EmployeeBenefitService employeeBenefitService;

  @Autowired
  public EmployeeBenefitRestController(EmployeeBenefitService employeeBenefitService) {
    this.employeeBenefitService = employeeBenefitService;
  }

  @GetMapping
  public ResponseEntity<List<EmployeeBenefit>> findAll() {
    List<EmployeeBenefit> employeeBenefits = employeeBenefitService.findAll();
    return ResponseEntity.ok(employeeBenefits);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeBenefit> findById(@PathVariable Long id) {
    Optional<EmployeeBenefit> benefit = employeeBenefitService.findById(id);
    return benefit
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<EmployeeBenefit> create(@RequestBody EmployeeBenefit employeeBenefit) {
    if (employeeBenefit.getId() != null) {
      return ResponseEntity.badRequest().build();
    }
    EmployeeBenefit savedEmployeeBenefit = employeeBenefitService.save(employeeBenefit);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeBenefit);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Optional<EmployeeBenefit> employeeBenefit = employeeBenefitService.findById(id);
    if (employeeBenefit.isPresent()) {
      employeeBenefitService.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
