package by.lizaveta.shift.service;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class StatisticsCalculator {
    public static String calculateStatistics(Department department) {
        List<Employee> employees = department.getEmployees();
        int count = employees.size();

        if (count == 0) {
            return "0,0.00";
        }

        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        double avgSalary = new BigDecimal(totalSalary / count)
                .setScale(2, RoundingMode.UP)
                .doubleValue();

        return String.format("%d,%.2f", count, avgSalary);
    }
}
