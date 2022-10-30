package com.employees.employeessystem.models.repository;

import com.employees.employeessystem.models.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @autor Sebastian Londoño
 */

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    /**
     * Buscar empleado por email, principalmente para validar unicidad del email
     * @param email del empleado a consultar
     * @return optional de empleado
     */
    public Optional<Employee> findEmployeeByEmail(String email);

    /**
     * Buscar empleado por tipo y numero de documento
     * @param documentType
     * @param documentNumber
     * @return optional del empleado
     */
    @Query(value = "select * from employees where document_type = :dt and document_number = :dn", nativeQuery = true)
    public Optional<Employee> findByDocumentTypeAndDocumentNumber(@Param("dt") String documentType,@Param("dn") String documentNumber);
}
