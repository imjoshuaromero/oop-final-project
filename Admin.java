import java.util.Scanner; //allows user to input
public class Admin {
    Scanner sc = new Scanner(System.in);

    void viewAllConcerns(Grievance[] gList, int count) { //displays all submitted grievances
        if (count == 0) {
            System.out.println("No concerns submitted yet.");
            return;
        }
        for (int i = 0; i < count; i++) {
            gList[i].display();
        }
    }

    void updateConcern(Grievance[] gList, int count) { //update a grievance's status and feedback
        System.out.print("Enter Grievance ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < count; i++) { //searches through list of grievances
            if (gList[i].getId() == id) { //checks if the current grievance id matches what admin entered
                System.out.print("Enter new status (Under Review / In Progress / Resolved): ");
                String status = sc.nextLine();
                gList[i].setStatus(status);

                System.out.print("Enter feedback from officer: ");
                String feedback = sc.nextLine();
                gList[i].setFeedback(feedback);

                System.out.println("Concern updated successfully!");
                return;
            }
        }
        System.out.println("Grievance ID not found!");
    }

}

