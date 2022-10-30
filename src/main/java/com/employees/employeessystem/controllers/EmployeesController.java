package com.employees.employeessystem.controllers;
import com.employees.employeessystem.models.entity.Employee;
import com.employees.employeessystem.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @autor Sebastian Londoño
 * Controller con los endopoints api para consumir desde el frontend
 */
@RestController
@RequestMapping(value = "/employees")
@CrossOrigin(origins = "*")
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Get de todos los empleados
     * @return response con lista de empleados
     */
    @GetMapping("/find_all")
    public ResponseEntity<?> findAllEmployees() {
        List<Employee> listEmployees = (List<Employee>) employeeService.findAll();

        if(listEmployees.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(listEmployees);
    }

    /**
     * Get de un empleado por id
     * @param id del empleado a buscar
     * @return response con optional del empleado
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findByIdEmployee(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        if(optionalEmployee.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(optionalEmployee.get());
    }

    /**
     * Post del nuevo empleado
     * @param employee
     * @return response con el body del nuevo empleado creado
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){

        // Para validar si ya existe un empleado con el mismo numero y tipo de documento
        Optional<Employee> employeeOptional = employeeService.findByDocumentTypeAndDocumentNumber(employee.getDocumentType(), employee.getDocumentNumber());

        try {
            // Si existe un empleado con el mismo numero y tipo de documento se retorna response null
            if(employeeOptional.isPresent()){
                return ResponseEntity.noContent().build();
            }
            employeeService.save(employee);
        }
        /**
         * Captura de excepción cuando se intenta registrar un empleado con un correo ya existente
         */
        catch (DataIntegrityViolationException e) {
            String failedField = e.getMostSpecificCause().getMessage();
            if(failedField.contains("email")) {

                // Se hace llamado de servicio de validacion y generación del nuevo email
                String newEmailEmployee = employeeService.validateEmailRepeat(employee);

                employee.setEmail(newEmailEmployee);

                return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);

    }

    /**
     * Put de un empleado
     * @param employee
     * @param id del empleado a actualizar
     * @return response con body del empleado actualizado
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        if(optionalEmployee.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Employee employeeUpdate = optionalEmployee.get();
        employeeUpdate.setFirstLastName(employee.getFirstLastName());
        employeeUpdate.setSecondLastName(employee.getSecondLastName());
        employeeUpdate.setFisrtName(employee.getFisrtName());
        employeeUpdate.setOtherNames(employee.getOtherNames());
        employeeUpdate.setOtherNames(employee.getOtherNames());
        employeeUpdate.setCountryJob(employee.getCountryJob());
        employeeUpdate.setDocumentType(employee.getDocumentType());
        employeeUpdate.setDocumentNumber(employee.getDocumentNumber());
        employeeUpdate.setEmail(employee.getEmail());
        employeeUpdate.setIngressDate(employee.getIngressDate());
        employeeUpdate.setArea(employee.getArea());
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employeeUpdate));
    }

    /**
     * delete de un empleado
     * @param id del empleado a eliminar
     * @return response void ok
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        if(optionalEmployee.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
