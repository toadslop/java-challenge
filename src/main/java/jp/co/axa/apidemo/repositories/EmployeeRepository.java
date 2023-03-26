package jp.co.axa.apidemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jp.co.axa.apidemo.entities.Employee;

/**
 * Interface defining the methods for retrieving employees from a data source.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  // TODO: Fill this interface
}
