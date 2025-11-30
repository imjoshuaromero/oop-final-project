// Abstract parent class for all system users
// Cannot be instantiated directly - must use Student or Admin subclasses
public abstract class User {
    // Private fields for proper encapsulation
    private String userName;
    private String password;
    private String name;

    // Constructor for creating a new user
    public User(String userName, String password, String name) {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }

    // Getters for user information
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // Update user password
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // ABSTRACT METHODS - must be implemented by child classes
    // Different implementation for each user type
    public abstract void displayInfo();
    
    // ABSTRACT METHOD - each user type has different role
    public abstract String getRole();
}
