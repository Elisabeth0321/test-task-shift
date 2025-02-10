package by.lizaveta.shift.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Specialist {

    protected int id;

    protected String name;

    protected double salary;

}
