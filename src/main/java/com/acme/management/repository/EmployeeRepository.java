package com.acme.management.repository;

import com.acme.management.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

    Optional<Employee> findEmployeeByEmail(String email);

}
