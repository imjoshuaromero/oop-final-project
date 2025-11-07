// class para sa mga grievance/concern ng students
public class Grievance {
    // static kaya shared ng lahat, para sa auto-increment ng ID
    private static int nextId = 1;
    
    // private yung mga properties para sa encapsulation
    private int grievanceId;
    private String studentSR;
    private String title;
    private String category;
    private String description;
    private String status;
    private String feedback;

    // constructor para gumawa ng bagong grievance
    public Grievance(String studentSR, String title, String category, String description) {
        this.studentSR = studentSR;  // SR code ng student na nag-file
        this.title = title;  // title ng concern
        this.category = category;  // category ng concern
        this.description = description;  // detalye ng concern
        this.status = "Submitted";  // default status ay "Submitted"
        this.feedback = "None";  // wala pang feedback sa simula
        this.grievanceId = nextId++;  // bigyan ng ID tapos i-increment para sa next
    }

    // getter para makuha yung ID ng grievance
    public int getId() {
        return grievanceId;
    }

    // para makuha yung SR code ng student
    public String getStudentSR() {
        return studentSR;
    }

    // setter para i-update yung status ng grievance
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    // setter para i-update yung feedback
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // para i-display lahat ng info ng grievance
    public void display() {
        System.out.println("\nGrievance ID: " + grievanceId);
        System.out.println("Student SR-CODE: " + studentSR);
        System.out.println("Title: " + title);
        System.out.println("Category: " + category);
        System.out.println("Description: " + description);
        System.out.println("Status: " + status);
        System.out.println("Feedback: " + feedback);
    }
}