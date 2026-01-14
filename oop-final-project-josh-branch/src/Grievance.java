// Grievance class - represents student concerns/complaints
public class Grievance {
    // Static counter for auto-incrementing IDs
    private static int nextId = 1;
    
    // Private fields for encapsulation
    private int grievanceId;
    private String studentSr;
    private String title;
    private String category;
    private String description;
    private String status;
    private String feedback;

    // Constructor for creating a new grievance
    public Grievance(String studentSr, String title, String category, String description) {
        this.studentSr = studentSr;
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = "Submitted";  // Default status
        this.feedback = "None";  // Default feedback
        this.grievanceId = nextId++;  // Auto-increment ID
    }

    // Constructor for loading existing grievance from file
    public Grievance(int grievanceId, String studentSr, String title, String category, 
                    String description, String status, String feedback) {
        this.grievanceId = grievanceId;
        this.studentSr = studentSr;
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = status;
        this.feedback = feedback;
        // Update static counter if needed
        if (grievanceId >= nextId) {
            nextId = grievanceId + 1;
        }
    }

    // Getters for grievance fields
    public int getId() {
        return grievanceId;
    }

    public String getStudentSr() {
        return studentSr;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    // Setters for updating grievance
    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    // Display all grievance information in table format
    public void display() {
        int consoleWidth = Utility.getConsoleWidth();
        int tableWidth = 60;
        int leftPadding = (consoleWidth - tableWidth) / 2;
        String padding = " ".repeat(Math.max(0, leftPadding));
        
        // Column widths
        int labelWidth = 20;
        int valueWidth = tableWidth - labelWidth - 3; // 3 for borders
        
        // Top border
        System.out.println(padding + "╔" + "═".repeat(tableWidth - 2) + "╗");
        
        // Print each row with label on left, value on right
        printTableRow(padding, "Grievance ID", String.valueOf(grievanceId), labelWidth, valueWidth);
        printTableRow(padding, "Student SR-Code", studentSr, labelWidth, valueWidth);
        printTableRow(padding, "Title", title, labelWidth, valueWidth);
        printTableRow(padding, "Category", category, labelWidth, valueWidth);
        printTableRow(padding, "Description", description, labelWidth, valueWidth);
        printTableRow(padding, "Status", status, labelWidth, valueWidth);
        printTableRow(padding, "Feedback", feedback, labelWidth, valueWidth);
        
        // Bottom border
        System.out.println(padding + "╚" + "═".repeat(tableWidth - 2) + "╝");
    }
    
    // Helper method to print a table row with label and value
    private void printTableRow(String padding, String label, String value, int labelWidth, int valueWidth) {
        // Wrap long values if needed
        if (value.length() > valueWidth) {
            // Print first line with label
            String firstLine = value.substring(0, valueWidth);
            String labelPadded = String.format("%-" + labelWidth + "s", label + ":");
            System.out.println(padding + "║ " + labelPadded + firstLine + "║");
            
            // Print remaining lines without label
            int startIndex = valueWidth;
            while (startIndex < value.length()) {
                int endIndex = Math.min(startIndex + valueWidth, value.length());
                String line = value.substring(startIndex, endIndex);
                String emptyLabel = " ".repeat(labelWidth);
                String linePadded = String.format("%-" + valueWidth + "s", line);
                System.out.println(padding + "║ " + emptyLabel + linePadded + "║");
                startIndex = endIndex;
            }
        } else {
            // Single line - label on left, value on right
            String labelPadded = String.format("%-" + labelWidth + "s", label + ":");
            String valuePadded = String.format("%-" + valueWidth + "s", value);
            System.out.println(padding + "║ " + labelPadded + valuePadded + "║");
        }
    }
}