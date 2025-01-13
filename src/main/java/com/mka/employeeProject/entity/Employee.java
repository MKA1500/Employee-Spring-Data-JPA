package com.mka.employeeProject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name", nullable = false)
  @NotNull
  private String firstName;

  @Column(name = "last_name", nullable = false)
  @NotNull
  private String lastName;

  @Column(name = "email", nullable = false)
  @NotNull
  @Email
  private String email;

  @Column(name = "phone_number")
  private String phoneNumber;

  @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EmployeeBenefit> benefits = new ArrayList<>();

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "employee_detail_id", referencedColumnName = "id", unique = true)
  private EmployeeDetail employeeDetail;

  public Employee() {
  }

  public Employee(String firstName, String lastName, String email, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<EmployeeBenefit> getBenefits() {
    return benefits;
  }

  public void addBenefit(EmployeeBenefit benefit) {
    benefits.add(benefit);
    benefit.setEmployee(this);
  }

  public void removeBenefit(EmployeeBenefit benefit) {
    benefits.remove(benefit);
    benefit.setEmployee(null);
  }

  public EmployeeDetail getEmployeeDetail() {
    return employeeDetail;
  }

  public void setEmployeeDetail(EmployeeDetail employeeDetail) {
    this.employeeDetail = employeeDetail;
  }

  @Override
  public String toString() {
    return "Employee{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", employeeDetail=" + (employeeDetail != null ? employeeDetail.getId() : "null") +
        '}';
  }
}