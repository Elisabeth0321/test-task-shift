package by.lizaveta.shift.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Department {

    private String name;

    private Manager manager;

    private List<Employee> employees = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

}
