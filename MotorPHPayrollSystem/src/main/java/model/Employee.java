package model;

/**
 * Employee class that holds information related to an employee.
 * This class demonstrates several OOP principles:
 * - Inheritance: Extends the Person abstract class
 * - Polymorphism: Overrides abstract methods from Person
 * - Encapsulation: All fields are private with public getters
 * - Immutability: All fields are final to prevent modification after creation
 * - Information Hiding: Internal state is protected from outside manipulation
 * - Single Responsibility Principle: This class is only responsible for employee data
 */
public class Employee extends Person {
    // Private fields demonstrate encapsulation - data is hidden from other classes
    private final String employeeNumber;
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
        super(lastName, firstName, birthDate, address, contactNumber);
        this.employeeNumber = employeeNumber;
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
     * Implementation of the abstract method from Person.
     * Demonstrates polymorphism through method overriding.
     * 
     * @return The total compensation including salary and allowances
     */
    @Override
    public double computeCompensation() {
        return basicSalary + riceSubsidy + phoneAllowance + clothingAllowance;
    }
    
    /**
     * Implementation of the abstract method from Person.
     * Demonstrates polymorphism through method overriding.
     */
    @Override
    public void displayInformation() {
        System.out.println("Employee Information:");
        System.out.println("Name: " + getFullName());
        System.out.println("ID: " + employeeNumber);
        System.out.println("Position: " + position);
        System.out.println("Basic Salary: PHP " + String.format("%.2f", basicSalary));
        System.out.println("Total Compensation: PHP " + String.format("%.2f", computeCompensation()));
    }

    // Getter methods for Employee-specific fields
    /**
     * @return Employee's unique identifier
     */
    public String getEmployeeNumber() { return employeeNumber; }
    
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
    * Custom toString implementation for Employee class
    * @return String representation of the employee
    */
   @Override
   public String toString() {
       return "Employee: " + getFullName() + 
              "\nID: " + employeeNumber +
              "\nBirth Date: " + getBirthDate() +
              "\nAddress: " + getAddress() +
              "\nContact: " + getContactNumber() +
              "\nTIN: " + tinNumber +
              "\nSSS: " + sssNumber +
              "\nPhilHealth: " + philHealthNumber +
              "\nPag-IBIG: " + pagIbigNumber +
              "\nStatus: " + employmentStatus +
              "\nPosition: " + position +
              "\nSupervisor: " + supervisor +
              "\nBasic Salary: PHP " + String.format("%.2f", basicSalary) +
              "\nRice Subsidy: PHP " + String.format("%.2f", riceSubsidy) +
              "\nPhone Allowance: PHP " + String.format("%.2f", phoneAllowance) +
              "\nClothing Allowance: PHP " + String.format("%.2f", clothingAllowance);
   }
}