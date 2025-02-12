package by.lizaveta.shift.service.processing;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;

import java.util.Comparator;
import java.util.Map;

public class EmployeeSorter {

    public static void sortEmployees(Map<String, Department> departments, String criteria, String order) {
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

        for (Department department : departments.values()) {
            department.getEmployees().sort(comparator);
        }
    }

}