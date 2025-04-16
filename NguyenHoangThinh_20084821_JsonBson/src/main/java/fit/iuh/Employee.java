package fit.iuh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private long emp_id;
    private String name;
    private double salary;

    public Employee() {
    }

    public Employee(long emp_id, String name, double salary) {
        this.emp_id = emp_id;
        this.name = name;
        this.salary = salary;
    }
}
