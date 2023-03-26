package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

/**
 * Interface class defining the methods available for the Employee service.
 */
public interface EmployeeService {

    /**
     * Get a list of all available employees.
     */
    public List<Employee> retrieveEmployees();

    /**
     * Get a single employee buy his/her employeeId.
     */
    public Optional<Employee> getEmployee(Long employeeId);

    /**
     * Saves a new employee to the database. TODO: does this do anything if the employee already
     * exists?
     */
    public void saveEmployee(EmployeeDto employee);

    /**
     * Deletes the employee with the given employeeId;
     */
    public void deleteEmployee(Long employeeId);

    /**
     * Updates the provided employee. TODO: what does this do if the employee doesn't exist?
     */
    public void updateEmployee(EmployeeDto employee, long employeeId);
}
