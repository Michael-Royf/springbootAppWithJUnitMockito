package com.michael.service;

import com.michael.exception.ResourceNotFoundException;
import com.michael.model.Employee;
import com.michael.repository.EmployeeRepository;
import com.michael.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        // employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Michael")
                .lastName("Royf")
                .email("michael@gmail.com")
                .build();
    }

    //Junit test for save Employee method
    @Test
    @DisplayName("Junit test for save Employee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given - precondition or setup

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee))
                .willReturn(employee);
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //Junit test for save Employee method which throws exception
    @Test
    @DisplayName("Junit test for save Employee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenReturnThrowsException() {
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when -action or the behavior we are going to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    //Junit test for get all Employees method
    @Test
    @DisplayName("Junit test for get all Employees method")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeesList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        // when -action or the behavior we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit test for get all Employees method(negative scenario)
    @Test
    @DisplayName("Junit test for get all Employees method (negative scenario)")
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        //given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Tony")
                .lastName("Stark")
                .email("tony@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when -action or the behavior we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }


    //Junit test for get employee by id
    @Test
    @DisplayName("Junit test for get employee by id")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //Junit test for update employee method
    @Test
    @DisplayName("Junit test for update employee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {

        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("MICHAEL@gmail.com");
        employee.setFirstName("MICHAEL");

        // when -action or the behavior we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("MICHAEL@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("MICHAEL");
    }

    //Junit test for delete Employee method
    @Test
    @DisplayName("Junit test for delete Employee method")
    public void givenEmployeeId_whenDeleteEmplyee_thenNothing() {
        //given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(1L);

        // when -action or the behavior we are going to test
        employeeService.deleteEmployee(1L);

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
