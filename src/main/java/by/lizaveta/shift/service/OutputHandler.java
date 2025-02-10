package by.lizaveta.shift.service;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;
import by.lizaveta.shift.model.Manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputHandler {
    public static void printOrSaveData(Map<String, Department> departments, List<String> invalidData, String output, String filePath) throws IOException {
        StringBuilder outputText = new StringBuilder();

        for (Department department : departments.values()) {
            outputText.append(department.getName()).append("\n");
            Manager manager = department.getManager();
            if (manager != null) {
                outputText.append(String.format("Manager,%d,%s,%.2f\n", manager.getId(), manager.getName(), manager.getSalary()));
            }

            List<Employee> employees = department.getEmployees();
            for (Employee employee : employees) {
                outputText.append(String.format("Employee,%d,%s,%.2f\n", employee.getId(), employee.getName(), employee.getSalary()));
            }

            double avgSalary = employees.stream()
                    .collect(Collectors.averagingDouble(Employee::getSalary));

            outputText.append(String.format("%d,%.2f\n", employees.size(), avgSalary));
        }

        outputText.append("\nНекорректные данные:\n");
        for (String line : invalidData) {
            outputText.append(line).append("\n");
        }

        if ("console".equalsIgnoreCase(output)) {
            System.out.println(outputText);
        } else if ("file".equalsIgnoreCase(output)) {
            Files.write(Paths.get(filePath), outputText.toString().getBytes());
        } else {
            throw new IllegalArgumentException("Invalid output type: " + output);
        }
    }
}
