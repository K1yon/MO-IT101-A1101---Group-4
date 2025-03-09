package service;

import model.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import utils.PayslipGenerator;
import utils.SSSCalculator;
import utils.PhilHealthCalculator;
import utils.TaxCalculator;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PayrollSystem {
    private static final Logger LOGGER = Logger.getLogger(PayrollSystem.class.getName());
    private final List<Employee> employees = new ArrayList<>();

    // ✅ Constructor
    public PayrollSystem() {
        // Initialize employee list
    }

    // ✅ Load Employees from CSV
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

                if (line.length < 19) { // Ensure correct data length (Hourly Rate at index 18)
                    LOGGER.log(Level.WARNING, "⚠ Invalid data format: {0}", String.join("|", line));
                    continue;
                }

                try {
                    String employeeNumber = line[0].trim();
                    String lastName = line[1].trim();
                    String firstName = line[2].trim();
                    String birthDate = line[3].trim();
                    String address = line[4].trim();
                    String contactNumber = line[5].trim();
                    String tinNumber = line[6].trim();
                    String sssNumber = line[7].trim();
                    String philHealthNumber = line[8].trim();
                    String pagIbigNumber = line[9].trim();
                    String employmentStatus = line[10].trim();
                    String position = line[11].trim();
                    String supervisor = line[12].trim();
                    double basicSalary = parseSalary(line[13].trim());
                    double hourlyRate = parseSalary(line[18].trim()); // ✅ Ensure this matches CSV hourly rate column

                    // 🛠 Debugging Output
                    System.out.println("DEBUG: Employee " + firstName + " " + lastName +
                                       " - Basic Salary: " + basicSalary + 
                                       ", Hourly Rate: " + hourlyRate); 

                    employees.add(new Employee(
                        employeeNumber, lastName, firstName, birthDate, address, contactNumber,
                        tinNumber, sssNumber, philHealthNumber, pagIbigNumber,
                        employmentStatus, position, supervisor, basicSalary, hourlyRate
                    ));

                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "⚠ Error parsing numeric fields in line: {0}", String.join("|", line));
                }
            }
            System.out.println("✅ Employee data loaded successfully.");
        } catch (IOException | CsvException e) {
            LOGGER.log(Level.SEVERE, "❌ Error loading employee data: {0}", e.getMessage());
            throw e;
        }
    }

    // ✅ Process Payroll
    public void processPayroll(Employee emp, double hoursWorked) {
        double grossSalary = emp.getHourlyRate() * hoursWorked;

        // ✅ Compute government deductions
        double sss = SSSCalculator.getSSSContribution(grossSalary);
        double philHealth = PhilHealthCalculator.getPhilHealthContribution(grossSalary);
        double pagIbig = Math.min(grossSalary * 0.02, 100); // Pag-IBIG max is PHP 100

        // ✅ Compute taxable income AFTER deductions
        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double tax = TaxCalculator.computeWithholdingTax(taxableIncome);
        double netSalary = grossSalary - (sss + philHealth + pagIbig + tax);

        // ✅ Generate and display payslip
        PayslipGenerator.generatePayslip(emp, hoursWorked, grossSalary, sss, philHealth, pagIbig, tax, netSalary);
    }

    // ✅ Display Employee List
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("⚠ No employee data found.");
            return;
        }

        System.out.println("\n📋 Employee List:");
        System.out.println("------------------------------------------------------");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        System.out.println("------------------------------------------------------");
    }

    // ✅ Return List of Employees
    public List<Employee> getEmployees() {
        return employees;
    }

    // ✅ Parse Salary Safely
    private double parseSalary(String salary) {
        try {
            salary = salary.replace(",", "").trim();
            if (!salary.matches("\\d+(\\.\\d+)?")) { // Validate numeric format
                throw new NumberFormatException("Invalid salary format: " + salary);
            }
            return Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "⚠ Error parsing salary: {0}", salary);
            return 0.0;
        }
    }
}
