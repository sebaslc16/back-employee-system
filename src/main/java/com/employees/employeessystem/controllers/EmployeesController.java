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

@RestController
@RequestMapping(value = "/employees")
@CrossOrigin(origins = "*")
public class EmployeesController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/find_all")
    public ResponseEntity<?> findAllEmployees() {
        List<Employee> listEmployees = (List<Employee>) employeeService.findAll();

        if(listEmployees.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(listEmployees);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findByIdEmployee(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findById(id);

        if(optionalEmployee.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(optionalEmployee.get());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){

        Optional<Employee> employeeOptional = employeeService.findByDocumentTypeAndDocumentNumber(employee.getDocumentType(), employee.getDocumentNumber());

        try {
            if(employeeOptional.isPresent()){
                return ResponseEntity.noContent().build();
            }
            employeeService.save(employee);
        }
        catch (DataIntegrityViolationException e) {
            String failedField = e.getMostSpecificCause().getMessage();
            if(failedField.contains("email")) {

                String newEmailEmployee = employeeService.validateEmailRepeat(employee);

                employee.setEmail(newEmailEmployee);

                return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(employee));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);

    }

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
