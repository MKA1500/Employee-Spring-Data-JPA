package com.mka.employeeProject.rest;

import com.mka.employeeProject.entity.EmployeeDetail;
import com.mka.employeeProject.service.EmployeeDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employeeDetails")
public class EmployeeDetailRestController {

  private final EmployeeDetailService employeeDetailService;

  public EmployeeDetailRestController(EmployeeDetailService employeeDetailService) {
    this.employeeDetailService = employeeDetailService;
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDetail>> findAll() {
    List<EmployeeDetail> employeeDetails = employeeDetailService.findAll();
    return ResponseEntity.ok(employeeDetails);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDetail> findById(@PathVariable Long id) {
    Optional<EmployeeDetail> employeeDetail = employeeDetailService.findById(id);
    return employeeDetail
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<EmployeeDetail> create(@RequestBody EmployeeDetail employeeDetail) {
    if (employeeDetail.getId() != null) {
      // To avoid creating with an existing ID:
      return ResponseEntity.badRequest().build();
    }
    EmployeeDetail savedEmployeeDetail = employeeDetailService.save(employeeDetail);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDetail);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeDetail> update(
      @PathVariable Long id,
      @RequestBody EmployeeDetail updatedEmployeeDetail) {
      Optional<EmployeeDetail> existingDetail = employeeDetailService.findById(id);
      if (existingDetail.isPresent()) {
        EmployeeDetail detail = existingDetail.get();
        detail.setDepartment(updatedEmployeeDetail.getDepartment());
        detail.setRank(updatedEmployeeDetail.getRank());
        detail.setSalary(updatedEmployeeDetail.getSalary());
        EmployeeDetail saveDetail = employeeDetailService.save(detail);
        return ResponseEntity.ok(saveDetail);
      }
      return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    Optional<EmployeeDetail> employeeDetail = employeeDetailService.findById(id);
    if (employeeDetail.isPresent()) {
      employeeDetailService.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}










