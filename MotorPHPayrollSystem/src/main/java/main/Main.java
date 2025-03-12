package main;

import service.PayrollSystem;
import model.Employee;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();

        try {
            payrollSystem.loadEmployeeData("src/data/employee_data.csv");
            
            // ✅ Ensure `displayEmployees()` is called correctly
            payrollSystem.displayEmployees();

            // ✅ Retrieve employees
            List<Employee> employeeList = payrollSystem.getEmployees();
            if (employeeList.isEmpty()) {
                System.out.println("⚠ No employees found. Exiting payroll processing.");
                return;
            }

            // ✅ Scanner for user input
            try (Scanner scanner = new Scanner(System.in)) {
                for (Employee emp : employeeList) {
                    if (emp == null) continue; // Skip null entries

                    System.out.println("\nProcessing Payroll for: " + emp.getFullName());
                    System.out.println("======================================");
                    System.out.println(emp); // ✅ Displays complete employee details

                    System.out.print("Enter hours worked for " + emp.getFullName() + ": ");
                    
                    double hoursWorked = 0.0;
                    while (true) {
                        if (scanner.hasNextDouble()) {
                            hoursWorked = scanner.nextDouble();
                            if (hoursWorked >= 0) break; // Accept only non-negative values
                        } else {
                            System.out.println("⚠ Invalid input. Please enter a valid number.");
                        }
                        scanner.nextLine(); // Clear invalid input
                    }

                    payrollSystem.processPayroll(emp, hoursWorked);
                }
            }
        } catch (IOException | CsvException e) {
            System.err.println("❌ Error loading employee data: " + e.getMessage());
        }
    }
}
