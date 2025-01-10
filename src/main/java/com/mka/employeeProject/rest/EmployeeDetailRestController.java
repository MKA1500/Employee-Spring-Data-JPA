package com.mka.employeeProject.rest;

import com.mka.employeeProject.entity.EmployeeDetail;
import com.mka.employeeProject.service.EmployeeDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
