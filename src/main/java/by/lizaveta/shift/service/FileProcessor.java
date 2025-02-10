package by.lizaveta.shift.service;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;
import by.lizaveta.shift.model.Manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileProcessor {

    public static List<Manager> managers = new ArrayList<>();

    public static List<Employee> employees = new ArrayList<>();

    public static List<String> invalidData = new ArrayList<>();

    public static Map<String, Department> departments = new TreeMap<>();

    public static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    private static final List<Employee> pendingEmployees = new ArrayList<>();

    public static void parseData(List<String> lines) {

        for (String line : lines) {
            String[] parts = line.trim().split(",");
            if (parts.length < 5) {
                invalidData.add(line);
                continue;
            }

            try {
                String type = parts[0].trim();
                int id = Integer.parseInt(parts[1].trim());
                String name = parts[2].trim();
                double salary = parts[3].trim().isEmpty() ? 0.0 : Double.parseDouble(parts[3].trim());
                String lastField = parts[4].trim();

                if (salary < 0) {
                    invalidData.add(line);
                    continue;
                }

                if (type.equalsIgnoreCase("Manager")) {
                    Manager manager = new Manager(id, name, salary, lastField);
                    managers.add(manager);

                    departments.putIfAbsent(lastField, new Department(lastField));
                    Department department = departments.get(lastField);
                    department.setManager(manager);

                    pendingEmployees.removeIf(employee -> {
                        if (employee.getManagerId() == id) {
                            department.getEmployees().add(employee);
                            return true;
                        }
                        return false;
                    });
                } else if (type.equalsIgnoreCase("Employee")) {
                    int managerId = Integer.parseInt(lastField);
                    Employee employee = new Employee(id, name, salary, managerId);
                    employees.add(employee);

                    Optional<Manager> managerOpt = managers.stream()
                            .filter(m -> m.getId() == managerId)
                            .findFirst();

                    if (managerOpt.isPresent()) {
                        String departmentName = managerOpt.get().getDepartment();
                        departments.get(departmentName).getEmployees().add(employee);
                    } else {
                        pendingEmployees.add(employee);
                    }

                } else {
                    invalidData.add(line);
                }
            } catch (Exception e) {
                invalidData.add(line);
            }
        }

        for (Employee employee : pendingEmployees) {
            invalidData.add(String.format("Employee,%d,%s,%.2f,%d",
                    employee.getId(), employee.getName(), employee.getSalary(), employee.getManagerId()));
        }
        pendingEmployees.clear();
    }

}

