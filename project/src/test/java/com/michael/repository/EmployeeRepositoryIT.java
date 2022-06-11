package com.michael.repository;

import com.michael.integretion.AbstractionContainerBaseTest;
import com.michael.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT  extends AbstractionContainerBaseTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee1;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();
         employee1 = Employee.builder()
                .firstName("Michael")
                .lastName("Royf")
                .email("michael@gmail.com")
                .build();
    }


    //hunit test for save employee operation
    @Test
    @DisplayName("JUnit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        //given = precondition or setup


        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.save(employee1);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //Junit test for get all employees operation
    @Test
    @DisplayName("Junit test for get all employees operation")
    public void givenEmployee_whenFindAll_thenEmployeeList() {
        //given - precondition or setup

        Employee employee2 = Employee.builder()
                .firstName("Anna")
                .lastName("Vanyan")
                .email("anna@gmail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        // when -action or the behavior we are going to test
        List<Employee> employeeList = employeeRepository.findAll();
        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    //Junit test for get employee by id operation
    @Test
    @DisplayName("Junit test for get employee by id operation")
    public void givenEmployeeObject_whenFindBuId_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee employeeDB = employeeRepository.findById(employee1.getId()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for get employee by email operation
    @Test
    @DisplayName("Junit test for get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee1.getEmail()).get();
        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //Junit test for update employee operation
    @Test
    @DisplayName("Junit test for update employee operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.findById(employee1.getId()).get();
        savedEmployee.setEmail("MICHAEL@mailc.com");
        savedEmployee.setFirstName("Mich");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);
        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("MICHAEL@mailc.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Mich");
    }


    //Junit test for delete employee operation
    @Test
    @DisplayName("Junit test for delete employee operation")
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        //given - precondition or setup
        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        employeeRepository.deleteById(employee1.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee1.getId());
        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //Junit test for custom Query using JPQL with index
    @Test
    @DisplayName("Junit test for custom Query using JPQL with index")
    public void givenFirstNameAndLastName_whenFindBYJPQL_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        String firstName = "Michael";
        String lastname = "Royf";
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastname);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //Junit test for custom Query using JPQL with named params
    @Test
    @DisplayName("Junit test for custom Query using JPQL with  named params")
    public void givenFirstNameAndLastName_whenFindBYJPQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee1.getFirstName(), employee1.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //Junit test for custom query using SQL with index
    @Test
    @DisplayName("Junit test for custom query using SQL with index")
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee1.getFirstName(), employee1.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }


    //Junit test for custom query using SQL with  named params
    @Test
    @DisplayName("Junit test for custom query using SQL with named params")
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        //given - precondition or setup

        employeeRepository.save(employee1);
        // when -action or the behavior we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee1.getFirstName(), employee1.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

}
