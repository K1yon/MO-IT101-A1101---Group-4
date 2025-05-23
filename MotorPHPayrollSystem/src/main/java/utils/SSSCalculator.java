package utils;

/**
 * Utility class for computing SSS contributions based on salary brackets.
 * This class demonstrates:
 * - Polymorphism: Implements the Deductible interface
 * - Single Responsibility Principle: Focused solely on SSS calculations
 * - Encapsulation: Hides implementation details of SSS computation
 * - Strategy Pattern conceptually: Implements a specific algorithm for SSS calculation
 */
public class SSSCalculator implements Deductible {
    /**
     * Computes the SSS contribution based on salary.
     * Implements the tiered SSS contribution calculation based on the 2023 contribution table.
     * 
     * @param salary The employee's gross salary.
     * @return The SSS contribution amount.
     */
    public static double getSSSContribution(double salary) {
        // 2023 SSS Contribution Table
        if (salary < 4250) return 180.00;  // Minimum contribution
        if (salary <= 24749.99) return ((salary - 4000) / 500) * 22.50 + 180.00;
        return 1125.00; // Maximum contribution
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Demonstrates polymorphism through interface implementation.
     * 
     * @param salary The salary amount for deduction calculation
     * @return The calculated SSS deduction
     */
    @Override
    public double calculateDeduction(double salary) {
        return getSSSContribution(salary);
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Returns the type of deduction this calculator handles.
     * 
     * @return The deduction type name
     */
    @Override
    public String getDeductionType() {
        return "SSS Contribution";
    }
}