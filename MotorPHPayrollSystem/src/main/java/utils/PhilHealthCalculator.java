package utils;

/**
 * Utility class for computing PhilHealth contributions.
 */
public class PhilHealthCalculator {
    /**
     * Computes the PhilHealth contribution (3% of salary, with half paid by employee).
     * @param salary The employee's gross salary.
     * @return The employee's share of the PhilHealth contribution.
     */
    public static double getPhilHealthContribution(double salary) {
        return (salary * 0.03) / 2; // Employee pays half of the total 3%
    }
}
