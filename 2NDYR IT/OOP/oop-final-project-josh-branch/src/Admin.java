import java.util.ArrayList;

// Admin class - extends User with admin-specific capabilities
public class Admin extends User {

    // Constructor with specific admin info
    public Admin(String username, String password, String name) {
        super(username, password, name);
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

    // View all submitted concerns
    void viewAllConcerns(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("ALL CONCERNS");
        
        if (gList.isEmpty()) {
            Utility.printCentered("");
            Utility.printCentered("No concerns submitted yet.");
            Utility.printCentered("");
        } else {
            // Display all concerns
            for (Grievance grievance : gList) {
                grievance.display();
            }
        }
        Utility.waitForEnter("Press Enter to continue...");
    }

    // Update concern status and feedback
    void updateConcern(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("UPDATE CONCERN");
        
        int id = Utility.promptCenteredInt("Enter Grievance ID to update:");

        // Find and update concern
        for (Grievance grievance : gList) {
            if (grievance.getId() == id) {
                String status = Utility.promptCenteredString("Enter new status (Under Review / In Progress / Resolved):");
                grievance.setStatus(status);

                String feedback = Utility.promptCenteredString("Enter feedback from officer:");
                grievance.setFeedback(feedback);

                Utility.printCentered("");
                Utility.printCentered("Concern updated successfully!");
                Utility.printCentered("");
                Utility.waitForEnter("Press Enter to continue...");
                return;
            }
        }
        
        Utility.printCentered("");
        Utility.printCentered("Grievance ID not found!");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }

    // Delete a concern
    void deleteConcern(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("DELETE CONCERN");
        
        if (gList.isEmpty()) {
            Utility.printCentered("");
            Utility.printCentered("No concerns to delete.");
            Utility.printCentered("");
            Utility.waitForEnter("Press Enter to continue...");
            return;
        }

        int id = Utility.promptCenteredInt("Enter Grievance ID to delete:");

        // Find and delete concern
        for (int i = 0; i < gList.size(); i++) {
            if (gList.get(i).getId() == id) {
                String confirm = Utility.promptCenteredString("Are you sure you want to delete Grievance ID " + id + "? (yes/no):");
                
                if (confirm.equalsIgnoreCase("yes")) {
                    gList.remove(i);
                    Utility.printCentered("");
                    Utility.printCentered("Concern deleted successfully!");
                    Utility.printCentered("");
                } else {
                    Utility.printCentered("");
                    Utility.printCentered("Delete cancelled.");
                    Utility.printCentered("");
                }
                Utility.waitForEnter("Press Enter to continue...");
                return;
            }
        }
        
        Utility.printCentered("");
        Utility.printCentered("Grievance ID not found!");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
}