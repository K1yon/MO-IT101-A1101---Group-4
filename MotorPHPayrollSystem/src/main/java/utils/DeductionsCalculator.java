package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for computing government-mandated deductions.
 * This class demonstrates:
 * - Utility Class Pattern: Contains only static methods with no instance state
 * - Single Responsibility Principle: Focused only on deduction calculations
 * - Delegation: Coordinates calls to specialized calculator classes
 * - Facade Pattern: Provides a simplified interface to complex calculation subsystems
 * - Polymorphism: Uses interface and implementation classes for different deduction types
 */
public class DeductionsCalculator {
    // List of available deduction calculators - demonstrates polymorphism
    private static final List<Deductible> deductionCalculators = new ArrayList<>();
    
    static {
        // Initialize the list with available deduction calculators
        deductionCalculators.add(new SSSCalculator());
        deductionCalculators.add(new PhilHealthCalculator());
        deductionCalculators.add(new TaxCalculator());
    }
    
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
    
    /**
     * Polymorphic method to get all deductions for a salary.
     * Demonstrates abstraction and polymorphism by processing different types of deductions uniformly.
     * 
     * @param grossSalary The gross salary to calculate deductions from
     * @return List of DeductionResult objects containing deduction type and amount
     */
    public static List<DeductionResult> getAllDeductions(double grossSalary) {
        List<DeductionResult> results = new ArrayList<>();
        
        // Add Pag-IBIG manually since it has a different calculation method
        results.add(new DeductionResult("Pag-IBIG Contribution", calculatePagIbig(grossSalary)));
        
        // Polymorphically process all other deductions
        for (Deductible calculator : deductionCalculators) {
            if (calculator instanceof TaxCalculator) {
                // Need to calculate taxable income for tax
                double sss = calculateSSS(grossSalary);
                double philHealth = calculatePhilHealth(grossSalary);
                double pagIbig = calculatePagIbig(grossSalary);
                double taxableIncome = grossSalary - (sss + philHealth + pagIbig);
                results.add(new DeductionResult(calculator.getDeductionType(), calculator.calculateDeduction(taxableIncome)));
            } else {
                results.add(new DeductionResult(calculator.getDeductionType(), calculator.calculateDeduction(grossSalary)));
            }
        }
        
        return results;
    }
    
    /**
     * Inner class to hold deduction results.
     * Demonstrates encapsulation of related data.
     */
    public static class DeductionResult {
        private final String type;
        private final double amount;
        
        public DeductionResult(String type, double amount) {
            this.type = type;
            this.amount = amount;
        }
        
        public String getType() { return type; }
        public double getAmount() { return amount; }
    }
}