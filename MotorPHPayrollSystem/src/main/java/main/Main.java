package main;

import service.PayrollSystem;
import java.util.Scanner;
import java.io.IOException;
import com.opencsv.exceptions.CsvException;

/**
 * Main class to run the Payroll System.
 * This class serves as the entry point of the application and demonstrates:
 * - Single Responsibility Principle: Responsible only for initializing the system and user interaction
 * - Separation of Concerns: Delegates all payroll processing to PayrollSystem
 */
public class Main {
    /**
     * The main method that initiates the payroll application.
     * 
     * @param args Command line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Instantiate the PayrollSystem - Demonstrates object creation
        PayrollSystem payrollSystem = new PayrollSystem();

        try {
            // Load necessary data files
            payrollSystem.loadEmployeeData("src/data/employee_data.csv");
            payrollSystem.loadAttendanceRecords("src/data/attendance_records.csv"); // Load attendance data

            // Interactive console for processing payroll
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nEnter Employee Number to process payroll (or type 'exit' to quit): ");
                String employeeNumber = scanner.nextLine().trim();

                // Check for exit condition
                if (employeeNumber.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting payroll system.");
                    break;
                }

                // Process payroll for the specified employee
                payrollSystem.processPayroll(employeeNumber); // Retrieve and use attendance records
            }
            // Resource management - properly close the scanner
            scanner.close();
        } catch (IOException | CsvException e) {
            // Exception handling for file operations
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}