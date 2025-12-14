// Admin class - extends User with admin-specific capabilities
// Business logic moved to AdminOperationsImpl service class
public class Admin extends User {

    // Constructor with specific admin info
    public Admin(String userName, String password, String name) {
        super(userName, password, name);
    }

    // Default constructor for default admin account
    public Admin() {
        super("admin", "admin", "System Administrator");
    }

    // Implementation of abstract method from User class
    @Override
    public void displayInfo() {
        System.out.println("=== Administrator Information ===");
        System.out.println("Username: " + getUserName());
        System.out.println("Name: " + getName());
        System.out.println("Role: " + getRole());
        System.out.println("================================");
    }

    // Implementation of abstract method from User class
    @Override
    public String getRole() {
        return "Administrator";
    }
}