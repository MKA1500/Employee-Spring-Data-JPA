package com.mka.employeeProject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_benefit")
public class EmployeeBenefit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "cost", nullable = false)
  private Long cost;

  @ManyToOne(cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REFRESH,
      CascadeType.DETACH
  })
  @JoinColumn(name = "employee_id", nullable = false)
  @JsonBackReference
  private Employee employee;

  public EmployeeBenefit() {
  }

  public EmployeeBenefit(String name, Long cost) {
    this.name = name;
    this.cost = cost;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getCost() {
    return cost;
  }

  public void setCost(Long cost) {
    this.cost = cost;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String toString() {
    return "EmployeeBenefit{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", cost=" + cost +
        '}';
  }
}
