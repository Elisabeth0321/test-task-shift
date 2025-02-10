package by.lizaveta.shift.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends Specialist {

    private int managerId;

    public Employee(int id, String name, double salary, int managerId) {
        super(id, name, salary);
        this.managerId = managerId;
    }

}