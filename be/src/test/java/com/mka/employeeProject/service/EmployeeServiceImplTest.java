package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeRepository;
import com.mka.employeeProject.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeService;

  private Employee employee;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    employee = new Employee("John", "Doe", "john.doe@example.com", "1234567890");
    employee.setId(1L);
  }

  @Test
  void findAll_shouldReturnAllEmployees() {
    // Arrange
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

    // Act
    List<Employee> result = employeeService.findAll();

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
    verify(employeeRepository, times(1)).findAll();
  }

  @Test
  void findById_shouldReturnEmployee_whenExists() {
    // Arrange
    when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

    // Act
    Optional<Employee> result = employeeService.findById(1L);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get().getFirstName()).isEqualTo("John");
    verify(employeeRepository, times(1)).findById(1L);
  }

  @Test
  void findById_shouldReturnEmpty_whenNotExists() {
    // Arrange
    when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    Optional<Employee> result = employeeService.findById(1L);

    // Assert
    assertThat(result).isEmpty();
    verify(employeeRepository, times(1)).findById(1L);
  }

  @Test
  void save_shouldSaveAndReturnEmployee() {
    // Arrange
    when(employeeRepository.save(employee)).thenReturn(employee);

    // Act
    Employee result = employeeService.save(employee);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getFirstName()).isEqualTo("John");
    verify(employeeRepository, times(1)).save(employee);
  }

  @Test
  void deleteById_shouldDeleteEmployeeById() {
    // Act
    employeeService.deleteById(1L);

    // Assert
    verify(employeeRepository, times(1)).deleteById(1L);
  }

  @Test
  void save_shouldCallRepositoryWithCorrectArgument() {
    // Arrange
    ArgumentCaptor<Employee> argumentCaptor = ArgumentCaptor.forClass(Employee.class);
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    // Act
    employeeService.save(employee);

    // Assert
    verify(employeeRepository, times(1)).save(argumentCaptor.capture());
    Employee captured = argumentCaptor.getValue();
    assertThat(captured.getFirstName()).isEqualTo("John");
    assertThat(captured.getLastName()).isEqualTo("Doe");
  }
}
