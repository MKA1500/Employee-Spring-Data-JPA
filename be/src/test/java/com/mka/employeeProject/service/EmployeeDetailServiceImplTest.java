package com.mka.employeeProject.service;

import com.mka.employeeProject.dao.EmployeeDetailRepository;
import com.mka.employeeProject.entity.EmployeeDetail;
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

class EmployeeDetailServiceImplTest {

  @Mock
  private EmployeeDetailRepository employeeDetailRepository;

  @InjectMocks
  private EmployeeDetailServiceImpl employeeDetailService;

  private EmployeeDetail employeeDetail;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    employeeDetail = new EmployeeDetail("IT", 1, 75000L);
    employeeDetail.setId(1L);
  }

  @Test
  void findAll_shouldReturnAllEmployeeDetails() {
    // Arrange
    when(employeeDetailRepository.findAll()).thenReturn(Arrays.asList(employeeDetail));

    // Act
    List<EmployeeDetail> result = employeeDetailService.findAll();

    // Assert
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getDepartment()).isEqualTo("IT");
    verify(employeeDetailRepository, times(1)).findAll();
  }

  @Test
  void findById_shouldReturnEmployeeDetail_whenExists() {
    // Arrange
    when(employeeDetailRepository.findById(1L)).thenReturn(Optional.of(employeeDetail));

    // Act
    Optional<EmployeeDetail> result = employeeDetailService.findById(1L);

    // Assert
    assertThat(result).isPresent();
    assertThat(result.get().getDepartment()).isEqualTo("IT");
    verify(employeeDetailRepository, times(1)).findById(1L);
  }

  @Test
  void findById_shouldReturnEmpty_whenNotExists() {
    // Arrange
    when(employeeDetailRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    Optional<EmployeeDetail> result = employeeDetailService.findById(1L);

    // Assert
    assertThat(result).isEmpty();
    verify(employeeDetailRepository, times(1)).findById(1L);
  }

  @Test
  void save_shouldSaveAndReturnEmployeeDetail() {
    // Arrange
    when(employeeDetailRepository.save(employeeDetail)).thenReturn(employeeDetail);

    // Act
    EmployeeDetail result = employeeDetailService.save(employeeDetail);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getDepartment()).isEqualTo("IT");
    verify(employeeDetailRepository, times(1)).save(employeeDetail);
  }

  @Test
  void deleteById_shouldDeleteEmployeeDetailById() {
    // Act
    employeeDetailService.deleteById(1L);

    // Assert
    verify(employeeDetailRepository, times(1)).deleteById(1L);
  }

  @Test
  void save_shouldCallRepositoryWithCorrectArgument() {
    // Arrange
    ArgumentCaptor<EmployeeDetail> argumentCaptor = ArgumentCaptor.forClass(EmployeeDetail.class);
    when(employeeDetailRepository.save(any(EmployeeDetail.class))).thenReturn(employeeDetail);

    // Act
    employeeDetailService.save(employeeDetail);

    // Assert
    verify(employeeDetailRepository, times(1)).save(argumentCaptor.capture());
    EmployeeDetail captured = argumentCaptor.getValue();
    assertThat(captured.getDepartment()).isEqualTo("IT");
    assertThat(captured.getRank()).isEqualTo(1);
  }
}
