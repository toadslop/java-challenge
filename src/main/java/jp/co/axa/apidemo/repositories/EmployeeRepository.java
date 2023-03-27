package jp.co.axa.apidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import jp.co.axa.apidemo.entities.Employee;

/**
 * Interface defining the methods for retrieving {@link Employee} instances from a data source.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
 
}
