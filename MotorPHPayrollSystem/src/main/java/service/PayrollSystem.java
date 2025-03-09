package service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException; // ✅ Fixed import
import model.Employee;
import utils.PhilHealthCalculator;
import utils.SSSCalculator;
import utils.TaxCalculator;
import utils.PayslipGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PayrollSystem {
    private static final Logger LOGGER = Logger.getLogger(PayrollSystem.class.getName());
    private final List<Employee> employees = new ArrayList<>();

    // ✅ Add a No-Argument Constructor
    public PayrollSystem() {
        // Initialize the employee list
    }

    public void loadEmployeeData(String filePath) throws IOException, CsvValidationException {
        try (
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(new BufferedReader(fileReader))
        ) {
            String[] line;
            boolean firstLine = true;
            while ((line = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.length < 19) {
                    LOGGER.log(Level.WARNING, "⚠ Invalid data format: {0}", String.join("|", line));
                    continue;
                }

                try {
                    String employeeNumber = line[0].trim();
                    String lastName = line[1].trim();
                    String firstName = line[2].trim();
                    String birthDate = line[3].trim();
                    String address = line[4].trim();
                    String phoneNumber = line[5].trim();
                    String sssNumber = line[6].trim();
                    String philHealthNumber = line[7].trim();
                    String tinNumber = line[8].trim();
                    String pagIbigNumber = line[9].trim();
                    String position = line[10].trim();
                    String status = line[11].trim();
                    String supervisor = line[12].trim();
                    
                    String basicSalaryStr = line[13].trim();
                    String hourlyRateStr = line[18].trim();

                    double basicSalary = parseSalary(basicSalaryStr);
                    double hourlyRate = parseSalary(hourlyRateStr);

                    employees.add(new Employee(
                        employeeNumber, lastName, firstName, birthDate, 
                        address, phoneNumber, tinNumber, sssNumber,
                        philHealthNumber, pagIbigNumber, status,
                        position, supervisor, basicSalary, hourlyRate
                    ));

                } catch (NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "⚠ Error parsing numeric fields in line: {0}", String.join("|", line));
                }
            }
            System.out.println("✅ Employee data loaded successfully.");
        } catch (IOException | CsvValidationException e) {
            LOGGER.log(Level.SEVERE, "❌ Error loading employee data: {0}", e.getMessage());
            throw e;
        }
    }

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

    private double parseSalary(String salary) {
        try {
            salary = salary.replace(",", "").trim();
            if (!salary.matches("\\d+(\\.\\d+)?")) {
                throw new NumberFormatException("Invalid salary format: " + salary);
            }
            return Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "❌ Error parsing salary: {0}", salary);
            return 0.0;
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void processPayroll(Employee emp, double hoursWorked) {
        System.out.println("\n📝 Processing payroll for: " + emp.getFullName());

        PayrollCalculator payrollCalculator = new PayrollCalculator();

        // Calculate daily gross salary
        double grossSalary = payrollCalculator.computeGrossSalary(emp, hoursWorked);
        
        // Calculate monthly-based deductions and pro-rate them for daily pay
        double monthlyBasic = emp.getBasicSalary();
        
        // Calculate full monthly deductions
        double monthlySSS = SSSCalculator.getSSSContribution(monthlyBasic);
        double monthlyPhilHealth = PhilHealthCalculator.getPhilHealthContribution(monthlyBasic);
        double monthlyPagIbig = Math.min(monthlyBasic * 0.02, 100);
        
        // Pro-rate the deductions for daily pay (assuming 22 working days per month)
        double dailySSS = monthlySSS / 22.0;
        double dailyPhilHealth = monthlyPhilHealth / 22.0;
        double dailyPagIbig = monthlyPagIbig / 22.0;
        
        // Calculate daily taxable income
        double dailyTaxableIncome = grossSalary - (dailySSS + dailyPhilHealth + dailyPagIbig);
        
        // Calculate tax based on monthly taxable income
        double monthlyTaxableIncome = dailyTaxableIncome * 22.0; // Convert to monthly for tax brackets
        double monthlyTax = TaxCalculator.computeWithholdingTax(monthlyTaxableIncome);
        double dailyTax = monthlyTax / 22.0; // Convert back to daily tax
        
        // Calculate final net salary for the day
        double netSalary = grossSalary - (dailySSS + dailyPhilHealth + dailyPagIbig + dailyTax);

        payrollCalculator.displayPayroll(emp, hoursWorked);
        PayslipGenerator.generatePayslip(emp, hoursWorked, grossSalary, dailySSS, dailyPhilHealth, dailyPagIbig, dailyTax, netSalary);
    }
}
