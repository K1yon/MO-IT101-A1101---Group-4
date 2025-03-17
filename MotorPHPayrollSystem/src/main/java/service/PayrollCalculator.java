package service;

import model.Employee;
import utils.DeductionsCalculator;

/**
 * PayrollCalculator class computes salary components, including deductions and allowances.
 */
public class PayrollCalculator {

    /**
     * Computes the gross salary based on hourly rate and hours worked.
     * @param employee The employee whose salary is being calculated.
     * @param hoursWorked The number of hours worked by the employee.
     * @return The computed gross salary.
     */
    public double computeGrossSalary(Employee employee, double hoursWorked) {
        double hourlyRate = employee.getBasicSalary() / 168; // Standard work hours per month
        return hourlyRate * hoursWorked;
    }

    /**
     * Computes the total deductions including SSS, PhilHealth, PagIbig, and tax.
     * @param grossSalary The gross salary before deductions.
     * @return The total deductions amount.
     */
    public double computeDeductions(double grossSalary) {
        double sss = DeductionsCalculator.calculateSSS(grossSalary);
        double philHealth = DeductionsCalculator.calculatePhilHealth(grossSalary);
        double pagIbig = DeductionsCalculator.calculatePagIbig(grossSalary);
        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double withholdingtax = DeductionsCalculator.calculateTax(taxableIncome);

        return sss + philHealth + pagIbig + withholdingtax;
    }

    /**
     * Computes the net salary by subtracting deductions from the gross salary.
     * @param employee The employee whose salary is being computed.
     * @param hoursWorked The number of hours worked.
     * @return The net salary after deductions.
     */
    public double computeNetSalary(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double totalDeductions = computeDeductions(grossSalary);
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        return grossSalary - totalDeductions + totalAllowances;
    }

    /**
     * Displays the payroll summary for an employee.
     * @param employee The employee whose payroll is displayed.
     * @param hoursWorked The number of hours worked.
     */
    public void displayPayroll(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        double totalDeductions = computeDeductions(grossSalary);
        double netSalary = computeNetSalary(employee, hoursWorked);

        // Display payroll summary
        System.out.println("\n💰 Payroll Summary for " + employee.getFullName());
        System.out.println("-------------------------------------------------");
        System.out.printf("Gross Salary: PHP %.2f%n", grossSalary);
        System.out.printf("Total Allowances: PHP %.2f%n", totalAllowances);
        System.out.printf("Total Deductions: PHP %.2f%n", totalDeductions);
        System.out.printf("Net Salary: PHP %.2f%n", netSalary);
        System.out.println("-------------------------------------------------");
    }
}
