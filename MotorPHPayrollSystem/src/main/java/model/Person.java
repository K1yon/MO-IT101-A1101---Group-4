package model;

/**
 * Abstract base class for Person entities in the system.
 * This class demonstrates:
 * - Abstraction: Defines abstract methods that must be implemented by subclasses
 * - Encapsulation: All fields are private with protected getters
 * - Information Hiding: Internal state is protected from outside manipulation
 */
public abstract class Person {
    // Common attributes for all person types
    private final String lastName;
    private final String firstName;
    private final String birthDate;
    private final String address;
    private final String contactNumber;
    
    /**
     * Constructor for Person class.
     * 
     * @param lastName Person's last name
     * @param firstName Person's first name
     * @param birthDate Person's birth date
     * @param address Person's address
     * @param contactNumber Person's contact number
     */
    public Person(String lastName, String firstName, String birthDate, String address, String contactNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
    }
    
    /**
     * Abstract method for computing compensation.
     * Demonstrates abstraction by declaring a method without implementation.
     * 
     * @return The computed compensation amount
     */
    public abstract double computeCompensation();
    
    /**
     * Returns the full name of the person.
     * 
     * @return The combined first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * @return Person's birth date
     */
    public String getBirthDate() { return birthDate; }
    
    /**
     * @return Person's address
     */
    public String getAddress() { return address; }
    
    /**
     * @return Person's contact number
     */
    public String getContactNumber() { return contactNumber; }
    
    /**
     * Abstract method for displaying person information.
     * Must be implemented by subclasses to show relevant details.
     */
    public abstract void displayInformation();
}
