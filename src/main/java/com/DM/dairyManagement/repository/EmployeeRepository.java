package com.DM.dairyManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.DM.dairyManagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
