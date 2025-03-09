package utils;

import java.io.PrintStream;
import model.Employee;

public class PayslipGenerator {
    public static void generatePayslip(Employee emp, double hoursWorked, double grossSalary, 
                                       double sss, double philHealth, double pagIbig, 
                                       double withholdingTax, double netSalary) {
        System.out.println("\n===========================================");
        System.out.println("                📝 PAYSLIP                ");
        System.out.println("===========================================");
        System.out.printf("Employee: %-20s ID: %s%n", emp.getFullName(), emp.getEmployeeNumber());
        System.out.printf("Birthdate: %-30s%n", emp.getBirthDate());
        System.out.printf("Address: %-30s%n", emp.getAddress());
        System.out.printf("Phone Number: %-30s%n", emp.getContactNumber());
        System.out.printf("TIN#: %-30s%n", emp.getTinNumber()); // ✅ Fixed (was getTaxID())
        System.out.printf("SSS#: %-30s%n", emp.getSssNumber());
        System.out.printf("PhilHealth#: %-30s%n", emp.getPhilHealthNumber());
        System.out.printf("Pag-IBIG#: %-30s%n", emp.getPagIbigNumber());
        System.out.printf("Status: %-30s%n", emp.getEmploymentStatus());
        System.out.printf("Position: %-30s%n", emp.getPosition());
        System.out.printf("Supervisor: %-30s%n", emp.getSupervisor());
        System.out.println("-------------------------------------------");
        System.out.printf("Basic Salary:    PHP %,10.2f%n", emp.getBasicSalary());
        System.out.printf("Hourly Rate:     PHP %,10.2f%n", emp.getHourlyRate());
        System.out.printf("Hours Worked:    %10.2f%n", hoursWorked);
        System.out.printf("Gross Salary:    PHP %,10.2f%n", grossSalary);
        System.out.println("-------------------------------------------");
        System.out.println("📉 Deductions:");
        System.out.printf(" - Pag-IBIG:     PHP %,10.2f%n", Math.max(0, pagIbig));
        System.out.printf(" - PhilHealth:   PHP %,10.2f%n", Math.max(0, philHealth));
        System.out.printf(" - SSS:          PHP %,10.2f%n", Math.max(0, sss));
        System.out.printf(" - Tax:          PHP %,10.2f%n", Math.max(0, withholdingTax));
        System.out.println("-------------------------------------------");
        System.out.printf("💰 Net Salary:   PHP %,10.2f%n", netSalary);
        System.out.println("===========================================\n");
    }
}
