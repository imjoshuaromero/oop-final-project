// class para sa mga grievance/concern ng students
public class Grievance {
    // static kaya shared ng lahat, para sa auto-increment ng id
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
        this.studentSR = studentSR;  // sr code ng student na nag-file
        this.title = title;  // title ng concern
        this.category = category;  // category ng concern
        this.description = description;  // detalye ng concern
        this.status = "Submitted";  // default status ay "submitted"
        this.feedback = "None";  // wala pang feedback sa simula
        this.grievanceId = nextId++;  // bigyan ng id tapos i-increment para sa next
    }

    // getter para makuha yung id ng grievance
    public int getId() {
        return grievanceId;
    }

    // para makuha yung sr code ng student
    public String getStudentSR() {
        return studentSR;
    }

    // getter para title
    public String getTitle() {
        return title;
    }

    // getter para category
    public String getCategory() {
        return category;
    }

    // getter para description
    public String getDescription() {
        return description;
    }

    // getter para status
    public String getStatus() {
        return status;
    }

    // getter para feedback
    public String getFeedback() {
        return feedback;
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
        Utility.printCentered("");
        Utility.printCentered("Grievance ID: " + grievanceId);
        Utility.printCentered("Student SR-Code: " + studentSR);
        Utility.printCentered("Title: " + title);
        Utility.printCentered("Category: " + category);
        Utility.printCentered("Description: " + description);
        Utility.printCentered("Status: " + status);
        Utility.printCentered("Feedback: " + feedback);
        Utility.printCentered("");
    }
}