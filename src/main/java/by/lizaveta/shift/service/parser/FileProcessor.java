package by.lizaveta.shift.service.parser;

import by.lizaveta.shift.model.Department;
import by.lizaveta.shift.model.Employee;
import by.lizaveta.shift.model.Manager;
import by.lizaveta.shift.service.validation.DataValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileProcessor {

    public static List<Manager> managers = new ArrayList<>();
    public static List<Employee> employees = new ArrayList<>();
    public static List<String> invalidData = new ArrayList<>();
    public static Map<String, Department> departments = new TreeMap<>();

    private static final List<Employee> pendingEmployees = new ArrayList<>();
    private static final Set<Integer> uniqueIds = new HashSet<>();
    private static final Set<Integer> managerIds = new HashSet<>();

    public static List<String> readFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    public static void parseData(List<String> lines) {
        for (String line : lines) {
            processLine(line);
        }
        assignPendingEmployees();
    }

    private static void processLine(String line) {
        String[] parts = line.trim().split(",");
        if (parts.length < 5) {
            invalidData.add(line);
            return;
        }

        String type = parts[0].trim();
        String idStr = parts[1].trim();
        String name = parts[2].trim();
        String salaryStr = parts[3].trim();
        String lastField = parts[4].trim();

        if (DataValidator.isNotValidId(idStr)) {
            invalidData.add(line);
            return;
        }
        int id = Integer.parseInt(idStr);

        if (!uniqueIds.add(id)) {
            invalidData.add("Duplicate ID found: " + line);
            return;
        }

        double salary = parseSalary(salaryStr, line);
        if (salary == -1) return;

        if (type.equalsIgnoreCase("Manager")) {
            processManager(id, name, salary, lastField, line);
        } else if (type.equalsIgnoreCase("Employee")) {
            processEmployee(id, name, salary, lastField, line);
        } else {
            invalidData.add(line);
        }
    }

    private static double parseSalary(String salaryStr, String line) {
        if (!DataValidator.isValidSalary(salaryStr)) {
            invalidData.add(line);
            return -1;
        }
        return Double.parseDouble(salaryStr);
    }

    private static void processManager(int id, String name, double salary, String departmentName, String line) {
        if (!managerIds.add(id)) {
            invalidData.add("Duplicate Manager ID found: " + line);
            return;
        }

        Manager manager = new Manager(id, name, salary, departmentName);
        managers.add(manager);

        departments.putIfAbsent(departmentName, new Department(departmentName));
        Department department = departments.get(departmentName);
        department.setManager(manager);

        pendingEmployees.removeIf(employee -> {
            if (employee.getManagerId() == id) {
                department.getEmployees().add(employee);
                return true;
            }
            return false;
        });
    }

    private static void processEmployee(int id, String name, double salary, String managerIdStr, String line) {
        if (DataValidator.isNotValidId(managerIdStr)) {
            invalidData.add(line);
            return;
        }
        int managerId = Integer.parseInt(managerIdStr);

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
    }

    private static void assignPendingEmployees() {
        for (Employee employee : pendingEmployees) {
            invalidData.add(String.format("Employee,%d,%s,%.2f,%d",
                    employee.getId(), employee.getName(), employee.getSalary(), employee.getManagerId()));
        }
        pendingEmployees.clear();
    }

}