package jp.co.axa.apidemo.dto;

import jp.co.axa.apidemo.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for the {@link Employee} class. Only contains writable fields.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private String name;
    private Integer salary;
    private String department;
}
