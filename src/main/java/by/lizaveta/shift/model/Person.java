package by.lizaveta.shift.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Person {

    protected int id;

    protected String name;

    protected double salary;

}
