package service;

import model.Employee;
import utils.SSSCalculator;
import utils.PhilHealthCalculator;
import utils.TaxCalculator;

public class PayrollCalculator {

    public double computeGrossSalary(Employee employee, double hoursWorked) {
        return employee.getHourlyRate() * hoursWorked;
    }

    public double computeNetSalary(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        
        double sss = SSSCalculator.getSSSContribution(grossSalary);
        double philHealth = PhilHealthCalculator.getPhilHealthContribution(grossSalary);
        double pagIbig = Math.min(grossSalary * 0.02, 100);

        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double tax = TaxCalculator.computeWithholdingTax(taxableIncome);

        return taxableIncome - tax;
    }

    public void displayPayroll(Employee employee, double hoursWorked) {
        double grossSalary = computeGrossSalary(employee, hoursWorked);
        double sss = SSSCalculator.getSSSContribution(grossSalary);
        double philHealth = PhilHealthCalculator.getPhilHealthContribution(grossSalary);
        double pagIbig = Math.min(grossSalary * 0.02, 100);
        double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
        double tax = TaxCalculator.computeWithholdingTax(taxableIncome);
        double netSalary = taxableIncome - tax;

        System.out.println("\n💰 Payroll Summary for " + employee.getFullName());
        System.out.println("-------------------------------------------------");
        System.out.println("Gross Salary: PHP " + String.format("%.2f", grossSalary));
        System.out.println("SSS Deduction: PHP " + String.format("%.2f", sss));
        System.out.println("PhilHealth Deduction: PHP " + String.format("%.2f", philHealth));
        System.out.println("Pag-IBIG Deduction: PHP " + String.format("%.2f", pagIbig));
        System.out.println("Taxable Income: PHP " + String.format("%.2f", taxableIncome));
        System.out.println("Withholding Tax: PHP " + String.format("%.2f", tax));
        System.out.println("Net Salary: PHP " + String.format("%.2f", netSalary));
        System.out.println("-------------------------------------------------\n");
    }
}
