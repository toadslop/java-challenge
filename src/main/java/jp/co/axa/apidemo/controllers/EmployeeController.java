package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

/**
 * Controller for the employee api. Routes incoming requests to the proper repository methods.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * Sets a reference to the employee service.
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Api endpoing returning a list of{@link Employee} instances.
     * 
     * @return a List of {@link Employee} instances
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    /**
     * Api endpoing returning a list of employees.
     * 
     * @param employeeId the id number of the employee
     * @return an instance of {@link Employee}
     */
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        Optional<Employee> optEmp = employeeService.getEmployee(employeeId);
        if (optEmp.isPresent()) {
            return optEmp.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    }

    /**
     * Save information about a new employee.
     * 
     * @param employee the {@link Employee} to save
     */
    @PostMapping("/employees")
    public void saveEmployee(EmployeeDto employee) {
        employeeService.saveEmployee(employee);
        logger.info("Employee Saved Successfully");
    }

    /**
     * Api endpoing accepting an {@link Employee} id and deleting the corresponding employee if it
     * exists.
     * 
     * @param employeeId the id number of the employee
     */
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        logger.info("Employee Deleted Successfully");
    }

    /**
     * Api endpoing accepting an instance of {@link Employee} and updating the employee if it
     * exists.
     * 
     * @param employeeId the id number of the employee
     */
    @PutMapping("/employees/{employeeId}")
    public void updateEmployee(@RequestBody EmployeeDto employee,
            @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployee(employee, employeeId);
            
    }

}
