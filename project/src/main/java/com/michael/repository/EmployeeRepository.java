package com.michael.repository;

import com.michael.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // define custom query  using JPQL with index params
    @Query("select e from  Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firsName, String lastName);

    // define custom query  using JPQL with named params
    @Query("select e from  Employee e where e.firstName = :firsName and e.lastName = :lastName")
    Employee findByJPQLNamedParams(@Param("firsName") String firsName, @Param("lastName") String lastName);


    // define custom query  using native SQL with index params
    @Query(value = "select * from employees e where  e.first_name = ?1 and e.last_name = ?2 ", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);


    // define custom query  using native SQL with named params
    @Query(value = "select * from employees e where  e.first_name = :firstName and e.last_name = :lastName ", nativeQuery = true)
    Employee findByNativeSQLNamed(@Param("firstName") String firstName, @Param("lastName") String lastName);


}
