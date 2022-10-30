package com.employees.employeessystem.services;

import com.employees.employeessystem.models.entity.Employee;
import com.employees.employeessystem.models.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @autor Sebastian Londoño
 * Implmentacion de los servicios con el repository
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public Employee save(Employee employee) { return employeeRepository.save(employee); }

    @Override
    @Transactional
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    /**
     * Validación del email del empleado cuando se intenta registrar uno que ya existe
     * @param employee
     * @return string con el nuevo email del empleado
     */
    @Override
    public String validateEmailRepeat(Employee employee) {

        // Secuencial para mantener unicidad del email
        int idSecuencialEmail = 1;
        String dominioEmail = "";
        String newEmailEmployee = "";
        Optional<Employee> employeeEmailValidation;

        // Validar dominio del email del empleado
        if(employee.getCountryJob().equals("Colombia")){
            dominioEmail = "cidenet.com.co";
        }
        else {
            dominioEmail = "cidenet.com.us";
        }

        /**
         * do while para generar de nuevo el email del empleado hasta que se conserve la unicidad de este
         */
        do{
            newEmailEmployee = employee.getFisrtName().toLowerCase()+"."+employee.getFirstLastName().toLowerCase()+"."+idSecuencialEmail+"@"+dominioEmail;
            employeeEmailValidation = findByEmail(newEmailEmployee);

            idSecuencialEmail++;

        }while (employeeEmailValidation.isPresent());

        return newEmailEmployee;
    }

    @Override
    public Optional<Employee> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber) {
        return employeeRepository.findByDocumentTypeAndDocumentNumber(documentType, documentNumber);
    }

}
