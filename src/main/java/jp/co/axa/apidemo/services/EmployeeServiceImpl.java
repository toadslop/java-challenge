package jp.co.axa.apidemo.services;

import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jp.co.axa.apidemo.dto.EmployeeDto;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    Formatter formatter = new Formatter();

    @Value("${app.cache.employee.max-capacity}")
    private int cacheCapacity;

    EmployeeServiceImpl() {
        logger.debug("Initializing EmployeeService.");
    }

    private LruCache<Long, Employee> cache = new LruCache<>(cacheCapacity);

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        logger.debug("Setting EmployeeRepository");
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> retrieveEmployees() {
        logger.debug("EmployeeService.retrieveEmployees called.");
        List<Employee> employees = employeeRepository.findAll();
        logger.debug("Found {} employees", employees.size());
        employees.stream().limit(cache.maxCapacity).forEach(emp -> cache.put(emp.getId(), emp));
        return employees;
    }

    public Optional<Employee> getEmployee(Long employeeId) {
        logger.info("EmployeeService.getEmployee called with id {}", employeeId);

        Optional<Employee> maybeEmp = cache.get(employeeId);
        if (maybeEmp.isPresent()) {
            logger.debug("Employee {} found in cache", employeeId);
            return maybeEmp;
        }
        
        maybeEmp = employeeRepository.findById(employeeId);
        if (maybeEmp.isPresent()) logger.debug("Employee {} found in cache", employeeId);
        else logger.debug("Employee {} not found", employeeId);

        return maybeEmp;
    }

    public void saveEmployee(EmployeeDto employee) {
        logger.debug("EmployeeService.saveEmployee called with {}", employee);
        Employee emp = Employee.fromDto(employee);
        Employee saved = employeeRepository.save(emp);
        cache.put(saved.getId(), saved);
    }

    public void deleteEmployee(Long employeeId){
        logger.debug("EmployeeService.deleteEmployee called with {}", employeeId);
        employeeRepository.deleteById(employeeId);
        cache.remove(employeeId);
    }

    public void updateEmployee(EmployeeDto employee, long employeeId) {
        logger.debug("EmployeeService.updateEmployee called with id {} and employee {}", employeeId, employee);
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        Employee finalEmployee;
        if (optEmp.isPresent()) {
            logger.debug("Employee exists. Updating existing employee");
            Employee currEmployee = optEmp.get();
            Employee updateEmployee = Employee.fromDto(employee);
            currEmployee.merge(updateEmployee);
            finalEmployee = employeeRepository.save(currEmployee);
        } else {
            logger.debug("Employee does not exist. Creating new employee");
            Employee currEmp = Employee.fromDto(employee);
            finalEmployee = employeeRepository.save(currEmp);
        }

        cache.put(finalEmployee.getId(), finalEmployee);
    }
}