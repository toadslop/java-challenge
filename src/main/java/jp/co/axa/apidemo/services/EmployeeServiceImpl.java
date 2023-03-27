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

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;

    private LruCache<Long, Employee> cache;

    public EmployeeServiceImpl(@Value("${app.cache.employee.max-capacity}") int cacheCapacity) {
        cache = new LruCache<>(cacheCapacity);
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        employees.stream().limit(cache.getMaxCapacity()).forEach(emp -> cache.put(emp.getId(), emp));
        return employees;
    }

    public Optional<Employee> getEmployee(Long employeeId) {
        Optional<Employee> maybeEmp = cache.get(employeeId);
        if (maybeEmp.isPresent()) return maybeEmp;
        
        return employeeRepository.findById(employeeId);
    }

    public Employee saveEmployee(EmployeeDto employee) {
        Employee emp = Employee.fromDto(employee);
        Employee saved = employeeRepository.save(emp);
        cache.put(saved.getId(), saved);
        return saved;
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
        cache.remove(employeeId);
    }

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