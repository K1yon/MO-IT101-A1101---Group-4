package utils;

/**
 * Interface for implementing deduction calculations.
 * This interface demonstrates:
 * - Abstraction: Defines methods without implementation
 * - Polymorphism: Allows different implementations of the same method
 */
public interface Deductible {
    /**
     * Calculates the deduction amount based on the given salary.
     * 
     * @param salary The salary amount to calculate deduction from
     * @return The calculated deduction amount
     */
    double calculateDeduction(double salary);
    
    /**
     * Gets the name or type of this deduction.
     * 
     * @return String representation of the deduction type
     */
    String getDeductionType();
}