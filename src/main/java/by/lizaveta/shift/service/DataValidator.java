package by.lizaveta.shift.service;

import java.util.regex.Pattern;

public class DataValidator {

    private static final Pattern SALARY_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?$");

    public static boolean isValidSalary(String salaryStr) {
        return salaryStr != null && SALARY_PATTERN.matcher(salaryStr).matches() && Double.parseDouble(salaryStr) > 0;
    }

    public static boolean isValidId(String idStr) {
        try {
            int id = Integer.parseInt(idStr);
            return id > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
