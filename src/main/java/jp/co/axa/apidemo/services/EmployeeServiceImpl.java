package jp.co.axa.apidemo.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.util.LruCache;

/**
 * Class defining the logic for CRUD actions on the Employee api.
 * 
 * @see Employee
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    /**
     * An instance of {@link EmployeeRepository}
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * An instance of {@link LruCache} to hold most recently accessed employees in memory.
     */
    private LruCache<Long, Employee> cache;

    /**
     * Instantiates the {@link EmployeeServiceImpl} with a given cache size. The cache size can be
     * specified in application.properties with the key 'app.cache.employee.max-capacity'.
     * 
     * @param cacheCapacity the maximum capacity of the cache
     */
    public EmployeeServiceImpl(@Value("${app.cache.employee.max-capacity}") int cacheCapacity) {
        cache = new LruCache<>(cacheCapacity);
    }

    /**
     * Set the {@link EmployeeRepository}.
     * 
     * @param employeeRepository the implementation of {@link EmployeeRepository}
     */
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get all the employees available in the database. Note that this will be inefficient for large
     * numbers of employees as paginated responses are not yet implemented. The returned items will
     * be stored in the cache up to the cache capacity. Any items beyond the size of the cahce are
     * ignored.
     * 
     * @return a {@link List} of {@link Employee} instances.
     */
    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        employees.stream().limit(cache.getMaxCapacity())
                .forEach(emp -> cache.put(emp.getId(), emp));
        return employees;
    }

    /**
     * Retrieve an employee by employeeId. The cache will be checked first. If not found, the
     * repository will be checked.
     * 
     * @param employeeId the id of the {@link Employee}
     * @return an {@link Optional} of {@link Employee}.
     */
    public Optional<Employee> getEmployee(Long employeeId) {
        Optional<Employee> maybeEmp = cache.get(employeeId);
        if (maybeEmp.isPresent())
            return maybeEmp;

        return employeeRepository.findById(employeeId);
    }

    /**
     * Saves the employee in the database and in the cache and returns the saved employee.
     * 
     * @param employee an instance of {@link EmployeeDto}.
     * @return the saved {@link Employee}.
     */
    public Employee saveEmployee(EmployeeDto employee) {
        Employee emp = Employee.fromDto(employee);
        Employee saved = employeeRepository.save(emp);
        cache.put(saved.getId(), saved);
        return saved;
    }

    /**
     * Deletes the employee with the given id, if it exists.
     * 
     * @param employeeId the id of the {@link Employee} to delete.
     */
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
        cache.remove(employeeId);
    }

    /**
     * Updates the employee if it exists or creates the employee if it doesn't.
     * 
     * @param employee the {@link EmployeeDto} containing the parameters to update.
     * @param employeeId the id of the {@link Employee} to update.
     */
    public Employee updateEmployee(EmployeeDto employee, long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        Employee finalEmployee;
        if (optEmp.isPresent()) {
            Employee currEmployee = optEmp.get();
            Employee updateEmployee = Employee.fromDto(employee);
            currEmployee.merge(updateEmployee);
            finalEmployee = employeeRepository.save(currEmployee);
        } else {
            Employee currEmp = Employee.fromDto(employee);
            finalEmployee = employeeRepository.save(currEmp);
        }

        cache.put(finalEmployee.getId(), finalEmployee);
        return finalEmployee;
    }
}
