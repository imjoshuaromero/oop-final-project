import java.util.ArrayList;

// admin class, nag-extend din ng user para ma-inherit yung properties
public class Admin extends User {
    // gumamit ng shared scanner helpers mula sa Utility.java

    // constructor kung may specific admin info
    public Admin(String username, String password, String name) {
        super(username, password, name);  // tumatawag sa constructor ng user
    }

    // default constructor para sa default admin account
    public Admin() {
        super("admin", "admin", "system administrator");  // default username at password ay "admin"
    }

    // override ng displayInfo para sa admin
    @Override
    public void displayInfo() {
        System.out.println("=== admin information ===");
        super.displayInfo();  // tumatawag sa displayInfo ng parent (User)
        System.out.println("role: administrator");
        System.out.println("========================");
    }

    // para makita lahat ng concerns na na-submit
    void viewAllConcerns(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("ALL CONCERNS");
        // check muna kung may mga concerns na
        if (gList.isEmpty()) {
            Utility.printCentered("");
            Utility.printCentered("No concerns submitted yet.");
            Utility.printCentered("");
        } else {
            // i-loop lahat ng concerns at i-display
            for (Grievance grievance : gList) {
                grievance.display();
            }
        }
        Utility.waitForEnter("Press Enter to continue...");
    }

    // para mag-update ng status at feedback ng concern
    void updateConcern(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("UPDATE CONCERN");
        // kunin yung id ng concern na i-uupdate
        int id = Utility.promptCenteredInt("Enter Grievance ID to update:");

        // hanapin yung concern na may matching id
        for (Grievance grievance : gList) {
            if (grievance.getId() == id) {
                // kunin yung bagong status
                String status = Utility.promptCenteredString("Enter new status (Under Review / In Progress / Resolved):");
                grievance.setStatus(status);  // i-update yung status

                // kunin naman yung feedback
                String feedback = Utility.promptCenteredString("Enter feedback from officer:");
                grievance.setFeedback(feedback);  // i-update yung feedback

                Utility.printCentered("");
                Utility.printCentered("Concern updated successfully!");
                Utility.printCentered("");
                Utility.waitForEnter("Press Enter to continue...");
                return;  // tapos na, lumabas na sa method
            }
        }
        // kung di naman nahanap yung id
        Utility.printCentered("");
        Utility.printCentered("Grievance ID not found!");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }

    // para mag-delete ng concern
    void deleteConcern(ArrayList<Grievance> gList) {
        Utility.clearScreen();
        Utility.printTitle("DELETE CONCERN");
        
        // check muna kung may mga concerns na
        if (gList.isEmpty()) {
            Utility.printCentered("");
            Utility.printCentered("No concerns to delete.");
            Utility.printCentered("");
            Utility.waitForEnter("Press Enter to continue...");
            return;
        }

        // kunin yung id ng concern na i-dedelete
        int id = Utility.promptCenteredInt("Enter Grievance ID to delete:");

        // hanapin yung concern na may matching id
        for (int i = 0; i < gList.size(); i++) {
            if (gList.get(i).getId() == id) {
                // tanungin muna para confirmation
                String confirm = Utility.promptCenteredString("Are you sure you want to delete Grievance ID " + id + "? (yes/no):");
                
                if (confirm.equalsIgnoreCase("yes")) {
                    gList.remove(i);  // i-delete yung concern
                    Utility.printCentered("");
                    Utility.printCentered("Concern deleted successfully!");
                    Utility.printCentered("");
                } else {
                    Utility.printCentered("");
                    Utility.printCentered("Delete cancelled.");
                    Utility.printCentered("");
                }
                Utility.waitForEnter("Press Enter to continue...");
                return;  // tapos na, lumabas na sa method
            }
        }
        
        // kung di naman nahanap yung id
        Utility.printCentered("");
        Utility.printCentered("Grievance ID not found!");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
}