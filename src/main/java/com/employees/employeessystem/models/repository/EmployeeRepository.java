package com.employees.employeessystem.models.repository;

import com.employees.employeessystem.models.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    public Optional<Employee> findEmployeeByEmail(String email);

    @Query(value = "select * from employees where document_type = :dt and document_number = :dn", nativeQuery = true)
    public Optional<Employee> findByDocumentTypeAndDocumentNumber(@Param("dt") String documentType,@Param("dn") String documentNumber);
}
