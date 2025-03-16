package utils;

/**
 * Utility class for computing SSS contributions based on salary brackets.
 */
public class SSSCalculator {
    /**
     * Computes the SSS contribution based on salary.
     * @param salary The employee's gross salary.
     * @return The SSS contribution amount.
     */
    public static double getSSSContribution(double salary) {
        // 2023 SSS Contribution Table
        if (salary < 4250) return 180.00;  // Minimum contribution
        if (salary <= 24749.99) return ((salary - 4000) / 500) * 22.50 + 180.00;
        return 1125.00; // Maximum contribution
    }
}




