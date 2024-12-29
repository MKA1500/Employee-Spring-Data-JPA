package com.mka.employeeProject.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_detail")
public class EmployeeDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "department")
  private String department;

  @Column(name = "rank")
  private Integer rank;

  @Column(name = "salary")
  private Long salary;

  public EmployeeDetail() {
  }

  public EmployeeDetail(String department, Integer rank, Long salary) {
    this.department = department;
    this.rank = rank;
    this.salary = salary;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Long getSalary() {
    return salary;
  }

  public void setSalary(Long salary) {
    this.salary = salary;
  }

  @Override
  public String toString() {
    return "EmployeeDetail{" +
        "id=" + id +
        ", department='" + department + '\'' +
        ", rank=" + rank +
        ", salary=" + salary +
        '}';
  }
}
