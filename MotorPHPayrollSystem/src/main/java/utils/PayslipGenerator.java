package utils;

import model.Employee;

public class PayslipGenerator {
    public static void generatePayslip(Employee emp, double hoursWorked, double grossSalary, 
                                       double sss, double philHealth, double pagIbig, 
                                       double withholdingTax, double netSalary) {
        System.out.println("\n===========================================");
        System.out.println("                📝 PAYSLIP                ");
        System.out.println("===========================================");
        System.out.printf("Employee: %-20s ID: %s%n", emp.getFullName(), emp.getEmployeeNumber());
        System.out.printf("Position: %-30s%n", emp.getPosition()); // ✅ Corrected Position
        System.out.printf("Status: %-30s%n", emp.getEmploymentStatus()); // ✅ Added Employment Status
        System.out.printf("Immediate Supervisor: %-20s%n", emp.getSupervisor()); // ✅ Added Supervisor
        System.out.printf("Birthday: %-30s%n", emp.getBirthDate()); // ✅ Added Birthday
        System.out.printf("Address: %-30s%n", emp.getAddress()); // ✅ Added Address
        System.out.printf("Phone Number: %-28s%n", emp.getContactNumber()); // ✅ Added Phone Number
        System.out.printf("SSS #: %-30s%n", emp.getSssNumber()); // ✅ Added SSS Number
        System.out.printf("PhilHealth #: %-30s%n", emp.getPhilHealthNumber()); // ✅ Added PhilHealth Number
        System.out.printf("TIN #: %-30s%n", emp.getTaxID()); // ✅ Fixed TIN instead of "Tax ID"
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