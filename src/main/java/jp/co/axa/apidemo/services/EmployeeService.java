package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;

/**
 * Interface class defining the methods available for the Employee service.
 * @see Employee
 */
public interface EmployeeService {

    /**
     * Get a list of all available employees.
     * @see Employee
     */
    public List<Employee> retrieveEmployees();

    /**
     * Get a single employee buy his/her employeeId.
     * @see Employee
     */
    public Optional<Employee> getEmployee(Long employeeId);

    /**
     * Saves a new employee to the database. Note that it is possible to save
     * two employees with identical information. They will be treated as two
     * different people who happen to have the same details.
     * @see Employee
     */
    public Employee saveEmployee(EmployeeDto employee);

    /**
     * Deletes the employee with the given employeeId
     * @see Employee
     */
    public void deleteEmployee(Long employeeId);

    /**
     * Updates the provided employee.
     * @see Employee
     */
    public Employee updateEmployee(EmployeeDto employee, long employeeId);
}
