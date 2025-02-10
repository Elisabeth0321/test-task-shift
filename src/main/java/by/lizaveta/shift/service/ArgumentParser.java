package by.lizaveta.shift.service;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {

    public static Map<String, String> parseArgs(String[] args) {
        Map<String, String> options = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i].trim();

            if (arg.startsWith("--")) {
                String[] parts = arg.substring(2).split("=", 2);
                if (parts.length == 2) {
                    options.put(parts[0], parts[1]);
                } else {
                    throw new IllegalArgumentException("Invalid argument: " + arg);
                }
            } else if (arg.startsWith("-")) {
                if (arg.equals("-s") || arg.equals("-o") || arg.equals("-p")) {
                    if (i + 1 < args.length) {
                        options.put(getFullOptionName(arg), args[i + 1]);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Flag " + arg + " requires a value.");
                    }
                } else {
                    throw new IllegalArgumentException("Unknown flag: " + arg);
                }
            }
        }

        validateOptions(options);

        return options;
    }

    private static String getFullOptionName(String shortFlag) {
        return switch (shortFlag) {
            case "-s" -> "sort";
            case "-o" -> "output";
            case "-p" -> "path";
            default -> throw new IllegalArgumentException("Unknown flag: " + shortFlag);
        };
    }

    private static void validateOptions(Map<String, String> options) {
        if (options.containsKey("order") && !options.containsKey("sort")) {
            throw new IllegalArgumentException("Sort order is specified without a sort type.");
        }

        if (options.containsKey("output") && options.get("output").equals("file") && !options.containsKey("path")) {
            throw new IllegalArgumentException("File output is specified, but no output path is provided.");
        }

        if (options.containsKey("sort")) {
            String sortType = options.get("sort");
            if (!sortType.equals("name") && !sortType.equals("salary")) {
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
            }

            options.putIfAbsent("order", "asc");
        }

        if (options.containsKey("order")) {
            String orderType = options.get("order");
            if (!orderType.equals("asc") && !orderType.equals("desc")) {
                throw new IllegalArgumentException("Invalid sort order: " + orderType);
            }
        }
    }

}