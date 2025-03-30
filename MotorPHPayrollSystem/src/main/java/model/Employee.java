package model;

/**
 * Employee class that holds information related to an employee.
 * This class demonstrates several OOP principles:
 * - Encapsulation: All fields are private with public getters
 * - Immutability: All fields are final to prevent modification after creation
 * - Information Hiding: Internal state is protected from outside manipulation
 * - Single Responsibility Principle: This class is only responsible for employee data
 */
public class Employee {
    // Private fields demonstrate encapsulation - data is hidden from other classes
    private final String employeeNumber;
    private final String lastName;
    private final String firstName;
    private final String birthDate;
    private final String address;
    private final String contactNumber;
    private final String tinNumber;
    private final String sssNumber;
    private final String philHealthNumber;
    private final String pagIbigNumber;
    private final String employmentStatus;
    private final String position;
    private final String supervisor;
    private final double basicSalary;
    private final double riceSubsidy;
    private final double phoneAllowance;
    private final double clothingAllowance;

    /**
     * Constructor for Employee class.
     * Demonstrates initialization of an immutable object where all fields are set at creation.
     * 
     * @param employeeNumber Unique identifier for the employee
     * @param lastName Employee's last name
     * @param firstName Employee's first name
     * @param birthDate Employee's birth date
     * @param address Employee's address
     * @param contactNumber Employee's contact number
     * @param tinNumber Employee's Tax Identification Number
     * @param sssNumber Employee's Social Security System number
     * @param philHealthNumber Employee's PhilHealth number
     * @param pagIbigNumber Employee's Pag-IBIG number
     * @param employmentStatus Employee's employment status (Regular, Probationary, etc.)
     * @param position Employee's job position
     * @param supervisor Employee's supervisor/manager
     * @param basicSalary Employee's basic monthly salary
     * @param riceSubsidy Employee's rice subsidy allowance
     * @param phoneAllowance Employee's phone allowance
     * @param clothingAllowance Employee's clothing allowance
     */
    public Employee(String employeeNumber, String lastName, String firstName, String birthDate, String address,
                    String contactNumber, String tinNumber, String sssNumber, String philHealthNumber, String pagIbigNumber,
                    String employmentStatus, String position, String supervisor, double basicSalary, 
                    double riceSubsidy, double phoneAllowance, double clothingAllowance) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.tinNumber = tinNumber;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.employmentStatus = employmentStatus;
        this.position = position;
        this.supervisor = supervisor;
        this.basicSalary = basicSalary;
        this.riceSubsidy = riceSubsidy;
        this.phoneAllowance = phoneAllowance;
        this.clothingAllowance = clothingAllowance;
    }

    /**
     * Returns the full name of the employee.
     * Demonstrates abstraction by providing a derived property from internal state.
     * 
     * @return The combined first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Getter methods for all fields - part of encapsulation, providing controlled access to internal state
    /**
     * @return Employee's unique identifier
     */
    public String getEmployeeNumber() { return employeeNumber; }
    
    /**
     * @return Employee's birth date
     */
    public String getBirthDate() { return birthDate; }
    
    /**
     * @return Employee's address
     */
    public String getAddress() { return address; }
    
    /**
     * @return Employee's contact number
     */
    public String getContactNumber() { return contactNumber; }
    
    /**
     * @return Employee's TIN number
     */
    public String getTinNumber() { return tinNumber; }
    
    /**
     * @return Employee's SSS number
     */
    public String getSssNumber() { return sssNumber; }
    
    /**
     * @return Employee's PhilHealth number
     */
    public String getPhilHealthNumber() { return philHealthNumber; }
    
    /**
     * @return Employee's Pag-IBIG number
     */
    public String getPagIbigNumber() { return pagIbigNumber; }
    
    /**
     * @return Employee's employment status
     */
    public String getEmploymentStatus() { return employmentStatus; }
    
    /**
     * @return Employee's position
     */
    public String getPosition() { return position; }
    
    /**
     * @return Employee's supervisor
     */
    public String getSupervisor() { return supervisor; }
    
    /**
     * @return Employee's basic salary
     */
    public double getBasicSalary() { return basicSalary; }
    
    /**
     * @return Employee's rice subsidy amount
     */
    public double getRiceSubsidy() { return riceSubsidy; }
    
    /**
     * @return Employee's phone allowance amount
     */
    public double getPhoneAllowance() { return phoneAllowance; }
    
    /**
     * @return Employee's clothing allowance amount
     */
    public double getClothingAllowance() { return clothingAllowance; }

     /**
 * Extended toString implementation - provides specific employee details
 */
@Override
public String toString() {
    return super.toString() + String.format(
            "\nStatus: %s\nPosition: %s\nSupervisor: %s\nBasic Salary: PHP %.2f\nRice Subsidy: PHP %.2f\nPhone Allowance: PHP %.2f\nClothing Allowance: PHP %.2f",
            employmentStatus, position, supervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance
);
}
}
