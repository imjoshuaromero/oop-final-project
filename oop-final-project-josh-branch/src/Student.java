// Student class - extends User with student-specific fields
public class Student extends User {
    // Private fields for encapsulation
    private String srCode;
    private String email;
    private boolean validated;

    // Constructor for creating a new student
    public Student(String srCode, String name, String email, String password) {
        super(srCode, password, name);  // Call parent constructor
        this.srCode = srCode;
        this.email = email;
        this.validated = false;  // Default: not validated
    }

    // Getters for student-specific fields
    public String getSrCode() {
        return srCode;
    }

    public String getEmail() {
        return email;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    // Implementation of abstract method from User class
    @Override
    public void displayInfo() {
        System.out.println("=== Student Information ===");
        System.out.println("SR-Code: " + srCode);
        System.out.println("Username: " + getUserName());
        System.out.println("Name: " + getName());
        System.out.println("Email: " + email);
        System.out.println("Role: " + getRole());
        System.out.println("========================");
        
    }
    
    // Implementation of abstract method from User class
    @Override
    public String getRole() {
        return "Student";
    }
}