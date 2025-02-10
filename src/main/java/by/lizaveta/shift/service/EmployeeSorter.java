package by.lizaveta.shift.service;

import by.lizaveta.shift.model.Employee;

import java.util.Comparator;
import java.util.List;

public class EmployeeSorter {
    public static void sortEmployees(List<Employee> employees, String criteria, String order) {
        Comparator<Employee> comparator;

        if ("name".equalsIgnoreCase(criteria)) {
            comparator = Comparator.comparing(Employee::getName);
        } else if ("salary".equalsIgnoreCase(criteria)) {
            comparator = Comparator.comparingDouble(Employee::getSalary);
        } else {
            throw new IllegalArgumentException("Invalid sorting criteria: " + criteria);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        employees.sort(comparator);
    }
}

