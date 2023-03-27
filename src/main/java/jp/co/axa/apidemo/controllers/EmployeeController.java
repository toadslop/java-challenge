package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.Optional;
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
 * This controller defines the API interface for the the Employee Api and routes requests to the
 * proper handler methods.
 */
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    /**
     * @see jp.co.axa.apidemo.services.EmployeeServiceImpl
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * Sets a reference to the {@link jp.co.axa.apidemo.services.EmployeeService}.
     * 
     * @param employeeService {@link jp.co.axa.apidemo.services.EmployeeService}
     */
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Api endpoing returning a list of {@link Employee} instances.
     * 
     * @return a {@link List} of {@link Employee} instances
     */
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.retrieveEmployees();
    }

    /**
     * Api endpoint accepting an employee id url param and returning the employee if found.
     * 
     * @param employeeId the id number of the {@link Employee}
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
     * Save information about a new {@link Employee}.
     * 
     * @param employee the {@link Employee} to save
     * @return the newly saved {@link Employee}
     */
    @PostMapping("/employees")
    public Employee saveEmployee(EmployeeDto employee) {
        return employeeService.saveEmployee(employee);
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
    }

    /**
     * Api endpoing accepting an instance of {@link Employee} and updating the employee if it
     * exists.
     * 
     * @param employeeId the id number of the employee
     * @return the updated employee {@link Employee}
     */
    @PutMapping("/employees/{employeeId}")
    public Employee updateEmployee(@RequestBody EmployeeDto employee,
            @PathVariable(name = "employeeId") Long employeeId) {
        return employeeService.updateEmployee(employee, employeeId);

    }

}
