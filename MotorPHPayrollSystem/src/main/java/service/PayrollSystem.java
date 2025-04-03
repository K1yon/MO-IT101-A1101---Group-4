package service;

import model.Employee;
import utils.DeductionsCalculator;
import utils.PayslipGenerator;
import utils.TaxCalculator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages employee payroll operations, including data loading and payroll processing.
 * This class demonstrates:
 * - Façade Pattern: Provides a simplified interface to the complex payroll subsystem
 * - Separation of Concerns: Handles data loading, storage, and payroll processing
 * - Single Responsibility Principle: Each method has a focused purpose
 * - Composition: Utilizes various utility classes to perform specialized operations
 */
public class PayrollSystem {
    // Logger for error handling and system information - demonstrates good practice for error handling
    private static final Logger LOGGER = Logger.getLogger(PayrollSystem.class.getName());
    
    // Collection of employees - encapsulated as private field
    private final List<Employee> employees = new ArrayList<>();
    
    // Map structure for storing attendance records by employee and month - demonstrates appropriate data structure use
    private final Map<String, Map<String, Double>> attendanceRecords = new HashMap<>(); // Employee -> Month -> Hours Worked

    /**
     * Loads employee data from a CSV file.
     * Demonstrates file I/O operations and error handling.
     * 
     * @param filePath Path to the CSV file.
     * @throws IOException If file cannot be read
     * @throws CsvException If CSV parsing fails
     */
    public void loadEmployeeData(String filePath) throws IOException, CsvException {
        try (
            // Resource management with try-with-resources - ensures proper closing of resources
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(fileReader)
        ) {
            String[] line;
            boolean firstLine = true;
            while ((line = reader.readNext()) != null) {
                // Skip header row
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                try {
                    // Parse each line into an Employee object and add to collection
                    employees.add(parseEmployee(line));
                } catch (NumberFormatException e) {
                    // Graceful error handling for parsing issues
                    LOGGER.log(Level.WARNING, "⚠ Error parsing numeric fields: {0}", String.join("|", line));
                }
            }
            System.out.println("✅ Employee data loaded successfully.");
        } catch (IOException | CsvException e) {
            // Logging and re-throwing exceptions - maintains exception chain
            LOGGER.log(Level.SEVERE, "❌ Error loading employee data: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Parses a CSV line into an Employee object.
     * Demonstrates factory method pattern for object creation.
     * 
     * @param line Array of CSV values representing employee data
     * @return A new Employee object
     */
    private Employee parseEmployee(String[] line) {
        return new Employee(
            line[0].trim(), line[1].trim(), line[2].trim(), line[3].trim(), line[4].trim(),
            line[5].trim(), line[6].trim(), line[7].trim(), line[8].trim(), line[9].trim(),
            line[10].trim(), line[11].trim(), line[12].trim(),
            parseDouble(line[13]), parseDouble(line[14]), parseDouble(line[15]), parseDouble(line[16])
        );
    }

    /**
     * Parses a double value safely, handling formatting issues.
     * Demonstrates defensive programming and input validation.
     * 
     * @param value String representation of a number
     * @return Parsed double value, or 0.0 if parsing fails
     */
    private double parseDouble(String value) {
        try {
            // Remove commas and whitespace before parsing
            return Double.parseDouble(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Error parsing numeric value: " + value);
            return 0.0;
        }
    }

    /**
     * Loads and aggregates attendance records per employee per month.
     * Demonstrates data aggregation and complex data structure manipulation.
     * 
     * @param filePath Path to the CSV file.
     * @throws IOException If file cannot be read
     * @throws CsvException If CSV parsing fails
     */
    public void loadAttendanceRecords(String filePath) throws IOException, CsvException {
        try (
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(fileReader)
        ) {
            String[] line;
            boolean firstLine = true;
            while ((line = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                try {
                    // Extract relevant data from CSV record
                    String employeeNumber = line[0].trim(); // Employee ID
                    String date = line[3].trim(); // Date in MM/DD/YYYY format
                    double hoursWorked = parseDouble(line[4]); // Original hours worked

                    // Extract month and year to create a monthly key
                    String monthKey = date.substring(0, 2) + "/" + date.substring(6); // Extract MM/YYYY

                    // Use nested Map to aggregate hours by employee and month - demonstrates complex data structure
                    attendanceRecords.putIfAbsent(employeeNumber, new HashMap<>());
                    attendanceRecords.get(employeeNumber).put(monthKey,
                        attendanceRecords.get(employeeNumber).getOrDefault(monthKey, 0.0) + hoursWorked);
                } catch (Exception e) {
                    // Safe error handling to prevent one bad record from stopping the process
                    System.out.println("⚠ Skipping invalid record: " + Arrays.toString(line));
                }
            }
            System.out.println("✅ Attendance records loaded and aggregated successfully.");
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, "❌ Error loading attendance records: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Processes payroll by computing total salary based on attendance records.
     * Generates payslips for each month from June to December.
     * Demonstrates iteration through collection and delegation to specialized components.
     * 
     * @param employeeNumber The employee ID to process payroll for
     */
    public void processPayroll(String employeeNumber) {
        // Find the employee using Java 8 Stream API - demonstrates modern Java features
        Employee employee = employees.stream()
                .filter(e -> e.getEmployeeNumber().equals(employeeNumber))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("⚠ Employee not found.");
            return;
        }

        // Retrieve attendance records for the employee
        Map<String, Double> monthlyHours = attendanceRecords.getOrDefault(employeeNumber, new HashMap<>());
        String[] months = {"06/2024", "07/2024", "08/2024", "09/2024", "10/2024", "11/2024", "12/2024"};
        
        // Process each month's payroll
        for (String month : months) {
            Double totalHoursWorked = monthlyHours.getOrDefault(month, 0.0);
            if (totalHoursWorked == 0.0) {
                System.out.println("⚠ No attendance records found for " + employee.getFullName() + " in " + month);
                continue;
            }

            // Calculate payroll components
            double hourlyRate = employee.getBasicSalary() / 168; // Standard monthly hours
            double grossSalary = totalHoursWorked * hourlyRate;
            double sss = DeductionsCalculator.calculateSSS(grossSalary);
            double philHealth = DeductionsCalculator.calculatePhilHealth(grossSalary);
            double pagIbig = DeductionsCalculator.calculatePagIbig(grossSalary);
            double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
            double withholdingTax = TaxCalculator.computeWithholdingTax(taxableIncome);
            double totalDeductions = sss + philHealth + pagIbig + withholdingTax;
            double netSalary = grossSalary - totalDeductions;

            // Generate Payslip with correct monthly total - delegates to specialized class
            System.out.println("\n========================= PAYSLIP FOR " + month + " =========================\n");
            PayslipGenerator.generatePayslip(employee, totalHoursWorked, grossSalary, 
                                             totalDeductions, sss, philHealth, pagIbig,
                                             withholdingTax, netSalary);
        }
    }
}