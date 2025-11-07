public class Grievance {
    private static int nextId = 1; //only code inside the class, shared by all grievance objects
    private int grievanceId; //stores unique id 
    private String studentSR; 
    private String title;
    private String category; //what type of concern
    private String description; //full details
    private String status; //current state
    private String feedback; //admins response


    public Grievance(String studentSR, String title, String category, String description) { //Constructor,  requires 4 details
        this.studentSR = studentSR; //stores the provided information into objects
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = "Submitted"; //sets as default
        this.feedback = "None"; //sets as default when first filed
        this.grievanceId = nextId++; //assigns current value then increase nextID so the next grievance gets the next number
    }

    //GETTER
    public int getId() { //returns the grievance id
        return grievanceId;
    }


    public String getStudentSR() { //returns the sr-code of who submitted it
        return studentSR;
    }

    //SETTER
    public void setStatus(String newStatus) { //updates status
        this.status = newStatus;
    }


    public void setFeedback(String feedback) { //updates feedback
        this.feedback = feedback;
    }


    public void display() {
        System.out.println("\nGrievance ID: " + grievanceId);
        System.out.println("Student SR-CODE: " + studentSR);
        System.out.println("Title: " + title);
        System.out.println("Category: " + category);
        System.out.println("Description: " + description);
        System.out.println("Status: " + status);
        System.out.println("Feedback: " + feedback);
    } //readable or clear format
}

