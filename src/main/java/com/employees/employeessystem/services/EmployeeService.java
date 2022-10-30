package com.employees.employeessystem.services;

import com.employees.employeessystem.models.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * @autor Sebastian Londoño
 * Interface con los diferentes servicios a ultilizar
 */

public interface EmployeeService {
    public Iterable<Employee> findAll();

    public Employee save(Employee employee);

    public void deleteById(Long id);

    public Optional<Employee> findById(Long id);

    public Optional<Employee> findByEmail(String email);

    public String validateEmailRepeat(Employee employee);

    public Optional<Employee> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
}
