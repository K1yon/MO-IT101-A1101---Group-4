package utils;

/**
 * Utility class for computing withholding tax based on taxable income.
 * This class demonstrates:
 * - Polymorphism: Implements the Deductible interface
 * - Single Responsibility Principle: Focused solely on tax calculations
 * - Encapsulation: Hides implementation details of tax computation
 * - Open/Closed Principle: Can be extended without modification if tax brackets change
 */
public class TaxCalculator implements Deductible {
    /**
     * Computes the withholding tax based on the taxable income.
     * Implements the progressive tax calculation using the Philippine tax brackets.
     * 
     * @param taxableIncome The income subject to tax after deductions.
     * @return The computed withholding tax.
     */
    public static double computeWithholdingTax(double taxableIncome) {
        // Progressive tax rate implementation
        if (taxableIncome <= 20832) return 0.0;
        if (taxableIncome <= 33333) return (taxableIncome - 20833) * 0.20;
        if (taxableIncome <= 66667) return 2500 + (taxableIncome - 33333) * 0.25;
        if (taxableIncome <= 166667) return 10833 + (taxableIncome - 66667) * 0.30;
        if (taxableIncome <= 666667) return 40833.33 + (taxableIncome - 166667) * 0.32;
        return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Demonstrates polymorphism through interface implementation.
     * 
     * @param taxableIncome The income amount for tax calculation
     * @return The calculated withholding tax
     */
    @Override
    public double calculateDeduction(double taxableIncome) {
        return computeWithholdingTax(taxableIncome);
    }
    
    /**
     * Implementation of the Deductible interface method.
     * Returns the type of deduction this calculator handles.
     * 
     * @return The deduction type name
     */
    @Override
    public String getDeductionType() {
        return "Withholding Tax";
    }
}