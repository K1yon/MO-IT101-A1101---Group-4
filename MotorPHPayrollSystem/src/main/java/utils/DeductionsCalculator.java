package utils;

/**
 * Utility class for computing government-mandated deductions.
 */
public class DeductionsCalculator {
    /**
     * Computes Pag-IBIG contribution (capped at PHP 100).
     */
    public static double calculatePagIbig(double grossSalary) {
        return Math.min(grossSalary * 0.02, 100);
    }

    /**
     * Computes PhilHealth contribution (3% shared by employer and employee).
     */
    public static double calculatePhilHealth(double grossSalary) {
        return PhilHealthCalculator.getPhilHealthContribution(grossSalary);
    }

    /**
     * Computes SSS contribution based on salary brackets.
     */
    public static double calculateSSS(double grossSalary) {
        return SSSCalculator.getSSSContribution(grossSalary);
    }

    /**
     * Computes withholding tax based on taxable income.
     */
    public static double calculateTax(double taxableIncome) {
        return TaxCalculator.computeWithholdingTax(taxableIncome);
    }
}
