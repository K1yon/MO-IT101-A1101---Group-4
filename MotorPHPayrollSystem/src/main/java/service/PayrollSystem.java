package service;

import model.Employee;
import utils.DeductionsCalculator;
import utils.PayslipGenerator;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages employee payroll operations, including data loading and payroll processing.
 */
public class PayrollSystem {
    private static final Logger LOGGER = Logger.getLogger(PayrollSystem.class.getName());
    private final List<Employee> employees = new ArrayList<>();
    private final Map<String, Double> attendanceRecords = new HashMap<>();
    private static final double MAX_MONTHLY_HOURS = 168.0; // Cap hours worked per payroll period

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
     * Loads attendance records from a CSV file.
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
                    String logInTime = line[4].trim(); // Log In (Column E)
                    String logOutTime = line[5].trim(); // Log Out (Column F)

                    // Convert Log In and Log Out to total hours worked
                    double hoursWorked = calculateHoursWorked(logInTime, logOutTime);

                    // Sum total hours worked per employee
                    attendanceRecords.put(employeeNumber, attendanceRecords.getOrDefault(employeeNumber, 0.0) + hoursWorked);
                } catch (Exception e) {
                    System.out.println("⚠ Skipping invalid record: " + Arrays.toString(line));
                }
            }
            System.out.println("✅ Attendance records loaded successfully.");
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, "❌ Error loading attendance records: {0}", e.getMessage());
            throw e;
        }
    }

    /**
     * Converts Log In and Log Out times to total hours worked.
     */
    private double calculateHoursWorked(String logIn, String logOut) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date inTime = format.parse(logIn);
            Date outTime = format.parse(logOut);
            
            long diffInMillis = outTime.getTime() - inTime.getTime();
            return diffInMillis / (1000.0 * 60 * 60); // Convert milliseconds to hours
        } catch (ParseException e) {
            System.out.println("⚠ Invalid time format: LogIn = " + logIn + ", LogOut = " + logOut);
            return 0.0; // Default to 0 hours if invalid
        }
    }

    /**
     * Processes payroll by retrieving attendance records instead of manual input.
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

        Double totalHoursWorked = attendanceRecords.get(employeeNumber);
        if (totalHoursWorked == null) {
            System.out.println("⚠ No attendance records found for " + employee.getFullName());
            return;
        }

        double cappedHoursWorked = Math.min(totalHoursWorked, MAX_MONTHLY_HOURS);

        PayrollCalculator calculator = new PayrollCalculator();
        double grossSalary = calculator.computeGrossSalary(employee, cappedHoursWorked);
        double totalDeductions = calculator.computeDeductions(grossSalary);
        double netSalary = calculator.computeNetSalary(employee, cappedHoursWorked);

        PayslipGenerator.generatePayslip(employee, cappedHoursWorked, grossSalary, 
                                         totalDeductions,
                                         DeductionsCalculator.calculateSSS(grossSalary),
                                         DeductionsCalculator.calculatePhilHealth(grossSalary),
                                         DeductionsCalculator.calculatePagIbig(grossSalary),
                                         netSalary);
    }
}
