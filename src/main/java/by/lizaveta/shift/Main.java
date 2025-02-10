package by.lizaveta.shift;

import by.lizaveta.shift.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String, String> options = ArgumentParser.parseArgs(args);

            String inputFile = "input.txt";
            String output = options.getOrDefault("output", "console");
            String filePath = options.get("path");
            String sortCriteria = options.get("sort");
            String sortOrder = options.getOrDefault("order", "asc");

            List<String> lines = FileProcessor.readFile(inputFile);
            FileProcessor.parseData(lines);

            if (sortCriteria != null) {
                EmployeeSorter.sortEmployees(FileProcessor.departments, sortCriteria, sortOrder);
            }

            OutputHandler.printOrSaveData(FileProcessor.departments, FileProcessor.invalidData, output, filePath);
        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error in command line arguments: " + e.getMessage());
        }
    }

}