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
 */
public class PayrollSystem {
    private static final Logger LOGGER = Logger.getLogger(PayrollSystem.class.getName());
    private final List<Employee> employees = new ArrayList<>();
    private final Map<String, Map<String, Double>> attendanceRecords = new HashMap<>(); // Employee -> Month -> Hours Worked

    /**
     * Loads employee data from a CSV file.
     * @param filePath Path to the CSV file.
     */
    public void loadEmployeeData(String filePath) throws IOException, CsvException {
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
                    employees.add(parseEmployee(line));
                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "⚠ Error parsing numeric fields: {0}", String.join("|", line));
                }
            }
            System.out.println("✅ Employee data loaded successfully.");
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, "❌ Error loading employee data: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Parses a CSV line into an Employee object.
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
     * Parses a double value safely.
     */
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value.replace(",", "").trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠ Error parsing numeric value: " + value);
            return 0.0;
        }
    }

    /**
     * Loads and aggregates attendance records per employee per month.
     * @param filePath Path to the CSV file.
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
                    String employeeNumber = line[0].trim(); // Employee ID
                    String date = line[3].trim(); // Date in MM/DD/YYYY format
                    double hoursWorked = parseDouble(line[4]); // Original hours worked

                    String monthKey = date.substring(0, 2) + "/" + date.substring(6); // Extract MM/YYYY

                    attendanceRecords.putIfAbsent(employeeNumber, new HashMap<>());
                    attendanceRecords.get(employeeNumber).put(monthKey,
                        attendanceRecords.get(employeeNumber).getOrDefault(monthKey, 0.0) + hoursWorked);
                } catch (Exception e) {
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
     */
    public void processPayroll(String employeeNumber) {
        Employee employee = employees.stream()
                .filter(e -> e.getEmployeeNumber().equals(employeeNumber))
                .findFirst()
                .orElse(null);

        if (employee == null) {
            System.out.println("⚠ Employee not found.");
            return;
        }

        Map<String, Double> monthlyHours = attendanceRecords.getOrDefault(employeeNumber, new HashMap<>());
        String[] months = {"06/2024", "07/2024", "08/2024", "09/2024", "10/2024", "11/2024", "12/2024"};
        
        for (String month : months) {
            Double totalHoursWorked = monthlyHours.getOrDefault(month, 0.0);
            if (totalHoursWorked == 0.0) {
                System.out.println("⚠ No attendance records found for " + employee.getFullName() + " in " + month);
                continue;
            }

            double hourlyRate = employee.getBasicSalary() / 168; // Standard monthly hours
            double grossSalary = totalHoursWorked * hourlyRate;
            double sss = DeductionsCalculator.calculateSSS(grossSalary);
            double philHealth = DeductionsCalculator.calculatePhilHealth(grossSalary);
            double pagIbig = DeductionsCalculator.calculatePagIbig(grossSalary);
            double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
            double withholdingTax = TaxCalculator.computeWithholdingTax(taxableIncome);
            double totalDeductions = sss + philHealth + pagIbig + withholdingTax;
            double netSalary = grossSalary - totalDeductions;

            // ✅ Generate Payslip with correct monthly total
            System.out.println("\n========================= PAYSLIP FOR " + month + " =========================\n");
            PayslipGenerator.generatePayslip(employee, totalHoursWorked, grossSalary, 
                                             totalDeductions, sss, philHealth, pagIbig,
                                             withholdingTax, netSalary);
        }
    }
}
