package main;

import service.PayrollSystem;
import java.util.Scanner;
import java.io.IOException;
import com.opencsv.exceptions.CsvException;

/**
 * Main class to run the Payroll System.
 */
public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();

        try {
            payrollSystem.loadEmployeeData("src/data/employee_data.csv");
            payrollSystem.loadAttendanceRecords("src/data/attendance_records.csv"); // ✅ Load attendance data

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\nEnter Employee Number to process payroll (or type 'exit' to quit): ");
                String employeeNumber = scanner.nextLine().trim();

                if (employeeNumber.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting payroll system.");
                    break;
                }

                payrollSystem.processPayroll(employeeNumber); // ✅ Retrieve and use attendance records
            }
            scanner.close();
        } catch (IOException | CsvException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }
}