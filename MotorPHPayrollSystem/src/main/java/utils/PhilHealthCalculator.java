package utils;

/**
 * Utility class for computing PhilHealth contributions.
 * This class demonstrates:
 * - Polymorphism: Implements the Deductible interface
 * - Single Responsibility Principle: Focused solely on PhilHealth calculations
 * - Encapsulation: Hides implementation details of PhilHealth computation
 */
public class PhilHealthCalculator implements Deductible {
    /**
     * Computes the PhilHealth contribution (3% of salary, with half paid by employee).
     * This method implements the PhilHealth contribution formula based on current regulations.
     * 
     * @param salary The employee's gross salary.
     * @return The employee's share of the PhilHealth contribution.
     */
    public static double getPhilHealthContribution(double salary) {
        // Employee pays half of the total 3% contribution
        return (salary * 0.03) / 2; 
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Demonstrates polymorphism through interface implementation.
     * 
     * @param salary The salary amount for deduction calculation
     * @return The calculated PhilHealth deduction
     */
    @Override
    public double calculateDeduction(double salary) {
        return getPhilHealthContribution(salary);
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Returns the type of deduction this calculator handles.
     * 
     * @return The deduction type name
     */
    @Override
    public String getDeductionType() {
        return "PhilHealth Contribution";
    }
}