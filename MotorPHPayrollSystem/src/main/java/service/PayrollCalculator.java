package service;

import model.Employee;
import utils.SSSCalculator;
import utils.PhilHealthCalculator;
import utils.TaxCalculator;

public class PayrollCalculator {

    public double computeGrossSalary(Employee employee, double hoursWorked) {
        double hourlyRate = employee.getBasicSalary() / 168; // ✅ Standard work hours per month
        return hourlyRate * hoursWorked;
    }

    public double computeNetSalary(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);

        double sss = SSSCalculator.getSSSContribution(grossSalary);
        double philHealth = PhilHealthCalculator.getPhilHealthContribution(grossSalary);
        double pagIbig = Math.min(grossSalary * 0.02, 100); // ✅ Pag-IBIG capped at PHP 100

        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double tax = TaxCalculator.computeWithholdingTax(taxableIncome);

        // ✅ Add Allowances (Rice, Phone, Clothing)
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        return (taxableIncome - tax) + totalAllowances;
    }

    public void displayPayroll(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double sss = SSSCalculator.getSSSContribution(grossSalary);
        double philHealth = PhilHealthCalculator.getPhilHealthContribution(grossSalary);
        double pagIbig = Math.min(grossSalary * 0.02, 100);
        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double tax = TaxCalculator.computeWithholdingTax(taxableIncome);
        double netSalary = taxableIncome - tax;

        // ✅ Include Allowances
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        double finalNetSalary = netSalary + totalAllowances;

        System.out.println("\n💰 Payroll Summary for " + employee.getFullName());
        System.out.println("-------------------------------------------------");
        System.out.printf(" %-30s PHP %12.2f %n", "Gross Salary:", grossSalary);
        System.out.printf(" %-30s PHP %12.2f %n", "SSS Deduction:", sss);
        System.out.printf(" %-30s PHP %12.2f %n", "PhilHealth Deduction:", philHealth);
        System.out.printf(" %-30s PHP %12.2f %n", "Pag-IBIG Deduction:", pagIbig);
        System.out.printf(" %-30s PHP %12.2f %n", "Taxable Income:", taxableIncome);
        System.out.printf(" %-30s PHP %12.2f %n", "Withholding Tax:", tax);
        System.out.printf(" %-30s PHP %12.2f %n", "Total Allowances:", totalAllowances);
        System.out.printf(" %-30s PHP %12.2f %n", "Final Net Salary:", finalNetSalary);
        System.out.println("-------------------------------------------------\n");
    }
}