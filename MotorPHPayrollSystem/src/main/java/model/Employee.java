package model;

public class Employee {
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
    private double basicSalary;
    private double hourlyRate;

    public Employee(String employeeNumber, String lastName, String firstName, String birthDate, 
                    String address, String contactNumber, String tinNumber, String sssNumber, 
                    String philHealthNumber, String pagIbigNumber, String employmentStatus, 
                    String position, String supervisor, double basicSalary, double hourlyRate) {
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
        this.basicSalary = Math.max(0, basicSalary);  
        this.hourlyRate = Math.max(0, hourlyRate);    
    }

    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
    public String getTinNumber() { return tinNumber; }
    public String getSssNumber() { return sssNumber; }
    public String getPhilHealthNumber() { return philHealthNumber; }
    public String getPagIbigNumber() { return pagIbigNumber; }
    public String getEmploymentStatus() { return employmentStatus; }
    public String getPosition() { return position; }
    public String getSupervisor() { return supervisor; }
    public double getBasicSalary() { return basicSalary; }
    public double getHourlyRate() { return hourlyRate; }

    public void setBasicSalary(double basicSalary) { this.basicSalary = Math.max(0, basicSalary); }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = Math.max(0, hourlyRate); }

    @Override
    public String toString() {
        return String.format(
                "Employee Number: %s\nName: %s\nBirthdate: %s\nAddress: %s\nPhone Number: %s\nTIN#: %s\nSSS#: %s\nPhilHealth#: %s\nPag-IBIG#: %s\nStatus: %s\nPosition: %s\nSupervisor: %s\nBasic Salary: PHP %.2f\nHourly Rate: PHP %.2f\n",
                employeeNumber, getFullName(), birthDate, address, contactNumber, tinNumber, sssNumber, philHealthNumber, pagIbigNumber, employmentStatus, position, supervisor, basicSalary, hourlyRate
        );
    }
}