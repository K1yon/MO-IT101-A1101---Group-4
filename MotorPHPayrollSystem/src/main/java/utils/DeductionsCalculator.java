package utils;

/**
 * Utility class for computing government-mandated deductions.
 * This class demonstrates:
 * - Utility Class Pattern: Contains only static methods with no instance state
 * - Single Responsibility Principle: Focused only on deduction calculations
 * - Delegation: Coordinates calls to specialized calculator classes
 * - Facade Pattern: Provides a simplified interface to complex calculation subsystems
 */
public class DeductionsCalculator {
    /**
     * Computes Pag-IBIG contribution (capped at PHP 100).
     * Demonstrates a business rule implementation with a maximum cap.
     * 
     * @param grossSalary The employee's gross salary
     * @return The computed Pag-IBIG contribution
     */
    public static double calculatePagIbig(double grossSalary) {
        return Math.min(grossSalary * 0.02, 100);
    }

    /**
     * Computes PhilHealth contribution (3% shared by employer and employee).
     * Demonstrates delegation to a specialized calculator class.
     * 
     * @param grossSalary The employee's gross salary
     * @return The computed PhilHealth contribution
     */
    public static double calculatePhilHealth(double grossSalary) {
        return PhilHealthCalculator.getPhilHealthContribution(grossSalary);
    }

    /**
     * Computes SSS contribution based on salary brackets.
     * Demonstrates delegation to a specialized calculator class.
     * 
     * @param grossSalary The employee's gross salary
     * @return The computed SSS contribution
     */
    public static double calculateSSS(double grossSalary) {
        return SSSCalculator.getSSSContribution(grossSalary);
    }

    /**
     * Computes withholding tax based on taxable income.
     * Demonstrates delegation to a specialized calculator class.
     * 
     * @param taxableIncome The employee's taxable income
     * @return The computed withholding tax
     */
    public static double calculateTax(double taxableIncome) {
        return TaxCalculator.computeWithholdingTax(taxableIncome);
    }
}