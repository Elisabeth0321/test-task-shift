package by.lizaveta.shift.service;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> options = new HashMap<>();

        for (String arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                if (parts.length == 2) {
                    options.put(parts[0], parts[1]);
                } else {
                    throw new IllegalArgumentException("Некорректный аргумент: " + arg);
                }
            }
        }

        if (options.containsKey("order") && !options.containsKey("sort")) {
            throw new IllegalArgumentException("Указан порядок сортировки без типа сортировки.");
        }

        if (options.containsKey("output") && options.get("output").equals("file") && !options.containsKey("path")) {
            throw new IllegalArgumentException("Указан вывод в файл, но путь к файлу не задан.");
        }

        return options;
    }
}

