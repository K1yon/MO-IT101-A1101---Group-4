package model;

public class Employee {
    private final String employeeNumber;
    private final String lastName;
    private final String firstName;
    private final String birthDate;
    private final String address;
    private final String contactNumber;
    private final String taxID;
    private final String sssNumber;
    private final String philHealthNumber;
    private final String pagIbigNumber;
    private final String employmentStatus;
    private final String position;
    private final String supervisor;
    private double basicSalary;
    private double hourlyRate;
    private String department; // Added department field

    public Employee(String employeeNumber, String lastName, String firstName, String birthDate, 
                    String address, String contactNumber, String taxID, String sssNumber, 
                    String philHealthNumber, String pagIbigNumber, String employmentStatus, 
                    String position, String supervisor, double basicSalary, double hourlyRate) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.taxID = taxID;
        this.sssNumber = sssNumber;
        this.philHealthNumber = philHealthNumber;
        this.pagIbigNumber = pagIbigNumber;
        this.employmentStatus = employmentStatus;
        this.position = position;
        this.supervisor = supervisor;
        this.basicSalary = Math.max(0, basicSalary);  // Prevents negative salary
        this.hourlyRate = Math.max(0, hourlyRate);    // Prevents negative hourly rate
        this.department = "General"; // Default department (Changeable if needed)
    }

    // ✅ Getter methods
    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public String getTaxID() { return taxID; }
    public String getSssNumber() { return sssNumber; }
    public String getPhilHealthNumber() { return philHealthNumber; }
    public String getPagIbigNumber() { return pagIbigNumber; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getPosition() { return position; }
    public String getSupervisor() { return supervisor; }
    public double getBasicSalary() { return basicSalary; }
    public double getHourlyRate() { return hourlyRate; }
    public String getDepartment() { return department; }

    // ✅ Setter methods
    public void setBasicSalary(double basicSalary) { this.basicSalary = Math.max(0, basicSalary); }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = Math.max(0, hourlyRate); }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format(
                "Employee Number: %s\nName: %s\nPosition: %s\nBasic Salary: PHP %.2f\nHourly Rate: PHP %.2f\nDepartment: %s\n",
                employeeNumber, getFullName(), position, basicSalary, hourlyRate, department
        );
    }
}
