import java.util.Scanner;
import java.util.ArrayList;

// Admin class, nag-extend din ng User para ma-inherit yung properties
public class Admin extends User {
    // scanner para makuha yung input ng user
    Scanner sc = new Scanner(System.in);

    // constructor kung may specific admin info
    public Admin(String username, String password, String name) {
        super(username, password, name);  // tumatawag sa constructor ng User
    }

    // default constructor para sa default admin account
    public Admin() {
        super("admin", "admin", "System Administrator");  // default username at password ay "admin"
    }

    // override ng displayInfo para sa admin
    @Override
    public void displayInfo() {
        System.out.println("=== Admin Information ===");
        super.displayInfo();  // tumatawag sa displayInfo ng parent (User)
        System.out.println("Role: Administrator");
        System.out.println("========================");
    }

    // para makita lahat ng concerns na na-submit
    void viewAllConcerns(ArrayList<Grievance> gList) {
        // check muna kung may mga concerns na
        if (gList.isEmpty()) {
            System.out.println("No concerns submitted yet.");
            return;
        }
        // i-loop lahat ng concerns at i-display
        for (Grievance grievance : gList) {
            grievance.display();
        }
    }

    // para mag-update ng status at feedback ng concern
    void updateConcern(ArrayList<Grievance> gList) {
        // kunin yung ID ng concern na i-uupdate
        System.out.print("Enter Grievance ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();  // para ma-clear yung input buffer

        // hanapin yung concern na may matching ID
        for (Grievance grievance : gList) {
            if (grievance.getId() == id) {
                // kunin yung bagong status
                System.out.print("Enter new status (Under Review / In Progress / Resolved): ");
                String status = sc.nextLine();
                grievance.setStatus(status);  // i-update yung status

                // kunin naman yung feedback
                System.out.print("Enter feedback from officer: ");
                String feedback = sc.nextLine();
                grievance.setFeedback(feedback);  // i-update yung feedback

                System.out.println("Concern updated successfully!");
                return;  // tapos na, lumabas na sa method
            }
        }
        // kung di naman nahanap yung ID
        System.out.println("Grievance ID not found!");
    }
}