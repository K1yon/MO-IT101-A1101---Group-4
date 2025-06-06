package utils;

import model.Employee;

/**
 * Utility class for generating and displaying employee payslips.
 * This class demonstrates:
 * - Utility Class Pattern: Contains only static methods with no instance state
 * - Separation of Concerns: Handles only the presentation of payroll data
 * - Single Responsibility Principle: Focused solely on payslip generation
 */
public class PayslipGenerator {
    /**
     * Generates and displays the payslip for an employee.
     * Demonstrates clean presentation logic separate from business logic.
     * 
     * @param employee The employee for whom the payslip is generated.
     * @param hoursWorked The number of hours worked by the employee.
     * @param grossSalary The computed gross salary.
     * @param totalDeductions The total deductions applied.
     * @param sss The SSS contribution.
     * @param philHealth The PhilHealth contribution.
     * @param pagIbig The Pag-IBIG contribution.
     * @param withholdingTax The withholding tax deduction.
     * @param netSalary The final computed net salary.
     */
    public static void generatePayslip(Employee employee, double hoursWorked, double grossSalary, 
                                       double totalDeductions, double sss, double philHealth, 
                                       double pagIbig, double withholdingTax, double netSalary) {

        // Calculate total allowances and final net salary
        double totalAllowances = employee.getRiceSubsidy() + employee.getPhoneAllowance() + employee.getClothingAllowance();
        double finalNetSalary = netSalary + totalAllowances;

        // Format and display the payslip in a structured manner
        System.out.println("\n===========================================");
        System.out.println("                 PAYSLIP                ");
        System.out.println("===========================================");
        System.out.printf(" Employee: %-22s ID: %s%n", employee.getFullName(), employee.getEmployeeNumber());
        System.out.printf(" Birthdate: %-30s%n", employee.getBirthDate());
        System.out.printf(" Address: %-40s%n", employee.getAddress());
        System.out.printf(" Phone No.: %-30s%n", employee.getContactNumber());
        System.out.printf(" TIN No.: %-30s%n", employee.getTinNumber());
        System.out.printf(" SSS No.: %-30s%n", employee.getSssNumber());
        System.out.printf(" PhilHealth No.: %-30s%n", employee.getPhilHealthNumber());
        System.out.printf(" Pag-IBIG No.: %-30s%n", employee.getPagIbigNumber());
        System.out.printf(" Status: %-30s%n", employee.getEmploymentStatus());
        System.out.printf(" Position: %-30s%n", employee.getPosition());
        System.out.printf(" Supervisor: %-30s%n", employee.getSupervisor());
        System.out.println("-------------------------------------------");
        System.out.printf(" %-30s PHP %12.2f%n", "Basic Salary:", employee.getBasicSalary());
        System.out.printf(" %-30s PHP %12.2f%n", "Hourly Rate:", (employee.getBasicSalary() / 168));  
        System.out.printf(" %-30s %10.2f%n", "Hours Worked:", hoursWorked);
        System.out.printf(" %-30s PHP %12.2f%n", "Gross Salary:", grossSalary);
        System.out.println("-------------------------------------------");
        System.out.println(" ✅ Allowances:");
        System.out.printf(" - %-27s PHP %12.2f%n", "Rice Subsidy:", employee.getRiceSubsidy());
        System.out.printf(" - %-27s PHP %12.2f%n", "Phone Allowance:", employee.getPhoneAllowance());
        System.out.printf(" - %-27s PHP %12.2f%n", "Clothing Allowance:", employee.getClothingAllowance());
        System.out.printf(" %-30s PHP %12.2f%n", "Total Allowances:", totalAllowances);
        System.out.println("-------------------------------------------");
        System.out.println(" ✅ Deductions:");
        System.out.printf(" - %-27s PHP %12.2f%n", "Pag-IBIG:", pagIbig);
        System.out.printf(" - %-27s PHP %12.2f%n", "PhilHealth:", philHealth);
        System.out.printf(" - %-27s PHP %12.2f%n", "SSS:", sss);
        System.out.printf(" - %-27s PHP %12.2f%n", "Withholding Tax:", withholdingTax);
        System.out.printf(" - %-27s PHP %12.2f%n", "Total Deductions:", totalDeductions);
        System.out.println("-------------------------------------------");
        System.out.printf(" ✅ %-30s PHP %12.2f%n", "Final Net Salary:", finalNetSalary);
        System.out.println("===========================================");
    }
}