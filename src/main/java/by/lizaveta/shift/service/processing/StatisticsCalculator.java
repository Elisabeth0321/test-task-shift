package by.lizaveta.shift.service.processing;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;
import by.lizaveta.shift.model.Manager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class StatisticsCalculator {

    public static String calculateStatistics(Department department) {
        List<Employee> employees = department.getEmployees();
        Manager manager = department.getManager();

        int count = employees.size() + 1;

        double totalSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        assert manager != null;
        totalSalary += manager.getSalary();

        double avgSalary = new BigDecimal(totalSalary / count)
                .setScale(2, RoundingMode.UP)
                .doubleValue();

        return String.format(Locale.US, "%d,%.2f", count, avgSalary);
    }

}