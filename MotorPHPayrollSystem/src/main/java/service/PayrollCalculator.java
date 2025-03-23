package service;

import model.Employee;
import utils.DeductionsCalculator;

/**
 * PayrollCalculator class computes salary components, including deductions and allowances.
 * This class demonstrates:
 * - Single Responsibility Principle: Focused solely on salary computation
 * - High Cohesion: All methods are related to payroll calculations
 * - Dependency Injection: Uses Employee objects passed as parameters
 * - Abstraction: Hides complex calculation details behind simple method interfaces
 */
public class PayrollCalculator {

    /**
     * Computes the gross salary based on hourly rate and hours worked.
     * Demonstrates abstraction by hiding the hourly rate calculation details.
     * 
     * @param employee The employee whose salary is being calculated.
     * @param hoursWorked The number of hours worked by the employee.
     * @return The computed gross salary.
     */
    public double computeGrossSalary(Employee employee, double hoursWorked) {
        // Standard work hours per month - could be moved to a constants class in a more complex system
        double hourlyRate = employee.getBasicSalary() / 168; 
        return hourlyRate * hoursWorked;
    }

    /**
     * Computes the total deductions including SSS, PhilHealth, PagIbig, and tax.
     * Demonstrates delegation by using specialized calculator classes for each deduction type.
     * 
     * @param grossSalary The gross salary before deductions.
     * @return The total deductions amount.
     */
    public double computeDeductions(double grossSalary) {
        // Using utility methods from DeductionsCalculator - demonstrates delegation
        double sss = DeductionsCalculator.calculateSSS(grossSalary);
        double philHealth = DeductionsCalculator.calculatePhilHealth(grossSalary);
        double pagIbig = DeductionsCalculator.calculatePagIbig(grossSalary);
        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double withholdingtax = DeductionsCalculator.calculateTax(taxableIncome);

        return sss + philHealth + pagIbig + withholdingtax;
    }

    /**
     * Computes the net salary by subtracting deductions from the gross salary and adding allowances.
     * Demonstrates composition by using results from other methods in this class.
     * 
     * @param employee The employee whose salary is being computed.
     * @param hoursWorked The number of hours worked.
     * @return The net salary after deductions and adding allowances.
     */
    public double computeNetSalary(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double totalDeductions = computeDeductions(grossSalary);
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        return grossSalary - totalDeductions + totalAllowances;
    }

    /**
     * Displays the payroll summary for an employee.
     * Demonstrates separation of concerns - calculation vs. presentation.
     * 
     * @param employee The employee whose payroll is displayed.
     * @param hoursWorked The number of hours worked.
     */
    public void displayPayroll(Employee employee, double hoursWorked) {
        // Calculate all required values by reusing existing methods - DRY principle
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        double totalDeductions = computeDeductions(grossSalary);
        double netSalary = computeNetSalary(employee, hoursWorked);

        // Display payroll summary - presentation logic
        System.out.println("\n💰 Payroll Summary for " + employee.getFullName());
        System.out.println("-------------------------------------------------");
        System.out.printf("Gross Salary: PHP %.2f%n", grossSalary);
        System.out.printf("Total Allowances: PHP %.2f%n", totalAllowances);
        System.out.printf("Total Deductions: PHP %.2f%n", totalDeductions);
        System.out.printf("Net Salary: PHP %.2f%n", netSalary);
        System.out.println("-------------------------------------------------");
    }
}