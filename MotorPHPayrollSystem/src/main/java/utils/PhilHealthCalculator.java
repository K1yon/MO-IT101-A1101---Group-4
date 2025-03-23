package utils;

/**
 * Utility class for computing PhilHealth contributions.
 * This class demonstrates:
 * - Utility Class Pattern: Contains only static methods
 * - Single Responsibility Principle: Focused solely on PhilHealth calculations
 * - Encapsulation: Hides implementation details of PhilHealth computation
 */
public class PhilHealthCalculator {
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
}