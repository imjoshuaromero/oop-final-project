import java.util.ArrayList;

/**
 * Implementation of student operations
 * Handles student registration, validation, and concern management
 */
public class StudentOperationsImpl implements IStudentOperations {
    
    @Override
    public void registerStudent(ArrayList<Student> students) {
        Utility.printTitle("STUDENT REGISTRATION");
        
        String srCode;
        while (true) {
            srCode = Utility.promptCenteredString("Enter SR-Code (XX-XXXXX): ");
            if (StudentValidator.isCampusStudent(srCode)) {
                if (isDuplicateStudent(students, srCode)) {
                    UIHelper.showError("SR-Code already registered!");
                    continue;
                }
                break;
            } else {
                UIHelper.showError("SR-Code not found in campus registry!");
            }
        }
        
        String name = Utility.promptCenteredString("Enter Full Name: ");
        
        String email;
        while (true) {
            email = Utility.promptCenteredString("Enter Email: ");
            if (StudentValidator.isValidEmail(email)) {
                break;
            } else {
                UIHelper.showError("Invalid email format!");
            }
        }
        
        String password;
        while (true) {
            password = Utility.promptCenteredString("Enter Password: ");
            String confirmPassword = Utility.promptCenteredString("Confirm Password: ");
            
            if (password.equals(confirmPassword)) {
                break;
            } else {
                UIHelper.showError("Passwords do not match! Please try again.");
            }
        }
        
        Student newStudent = new Student(srCode, name, email, PasswordUtils.hash(password));
        students.add(newStudent);
        DataManager.saveStudents(students);
        
        UIHelper.showSuccess("Registration successful! Please wait for admin validation.");
    }
    
    @Override
    public void validateRegisteredStudents(ArrayList<Student> students) {
        Utility.printTitle("VALIDATE STUDENTS");
        
        ArrayList<Student> unvalidatedStudents = new ArrayList<>();
        for (Student student : students) {
            if (!student.isValidated()) {
                unvalidatedStudents.add(student);
            }
        }
        
        if (unvalidatedStudents.isEmpty()) {
            UIHelper.showError("No students pending validation.");
            return;
        }
        
        Utility.printCentered("╔════════════════════════════════════════════════════════════════════════════════════════╗");
        Utility.printCentered("║                          STUDENTS PENDING VALIDATION                                   ║");
        Utility.printCentered("╠════════════════════════════════════════════════════════════════════════════════════════╣");
        Utility.printCentered(String.format("║ %-10s ║ %-30s ║ %-35s ║", "SR-CODE", "NAME", "EMAIL"));
        Utility.printCentered("╠════════════════════════════════════════════════════════════════════════════════════════╣");
        
        for (Student student : unvalidatedStudents) {
            Utility.printCentered(String.format("║ %-10s ║ %-30s ║ %-35s ║",
                    student.getSrCode(),
                    student.getName().length() > 30 ? student.getName().substring(0, 27) + "..." : student.getName(),
                    student.getEmail().length() > 35 ? student.getEmail().substring(0, 32) + "..." : student.getEmail()));
        }
        Utility.printCentered("╚════════════════════════════════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        String srCode = Utility.promptCenteredString("Enter SR-Code to validate (or 'back' to return): ");
        if (srCode.equalsIgnoreCase("back")) {
            return;
        }
        
        Student studentToValidate = findStudentBySrCode(students, srCode);
        if (studentToValidate != null && !studentToValidate.isValidated()) {
            studentToValidate.setValidated(true);
            DataManager.saveStudents(students);
            UIHelper.showSuccess("Student validated successfully!");
        } else {
            UIHelper.showError("Student not found or already validated.");
        }
    }
    
    @Override
    public void fileConcern(Student student, ArrayList<Grievance> grievances) {
        Utility.printTitle("FILE A CONCERN");
        
        String title = Utility.promptCenteredString("Enter concern title: ");
        
        Utility.printCentered("╔═══════════════════════════════════╗");
        Utility.printCentered("║       SELECT CATEGORY             ║");
        Utility.printCentered("╠═══════════════════════════════════╣");
        Utility.printCentered("║ 1. Academic                       ║");
        Utility.printCentered("║ 2. Facility                       ║");
        Utility.printCentered("║ 3. Administrative                 ║");
        Utility.printCentered("║ 4. Safety & Security              ║");
        Utility.printCentered("║ 5. Student Services               ║");
        Utility.printCentered("║ 6. Others                         ║");
        Utility.printCentered("╚═══════════════════════════════════╝");
        System.out.println();
        
        int categoryChoice = Utility.promptCenteredInt("Select category (1-6): ");
        String category;
        
        switch (categoryChoice) {
            case 1: category = "Academic"; break;
            case 2: category = "Facility"; break;
            case 3: category = "Administrative"; break;
            case 4: category = "Safety & Security"; break;
            case 5: category = "Student Services"; break;
            case 6: category = "Others"; break;
            default:
                UIHelper.showInvalidChoice(1, 6);
                return;
        }
        
        String description = Utility.promptCenteredString("Enter detailed description: ");
        
        int grievanceId = grievances.isEmpty() ? 1 : grievances.get(grievances.size() - 1).getId() + 1;
        Grievance newGrievance = new Grievance(grievanceId, student.getSrCode(), title, category, description, "Pending", "");
        
        grievances.add(newGrievance);
        DataManager.saveGrievances(grievances);
        
        UIHelper.showSuccess("Concern filed successfully! Grievance ID: " + grievanceId);
    }
    
    @Override
    public void viewStudentConcerns(Student student, ArrayList<Grievance> grievances) {
        ArrayList<Grievance> studentGrievances = new ArrayList<>();
        for (Grievance grievance : grievances) {
            if (grievance.getStudentSr().equals(student.getSrCode())) {
                studentGrievances.add(grievance);
            }
        }
        
        if (studentGrievances.isEmpty()) {
            UIHelper.showError("You have no filed concerns.");
            return;
        }
        
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) studentGrievances.size() / pageSize);
        
        for (int page = 0; page < totalPages; page++) {
            Utility.clearScreen();
            Utility.printTitle("MY CONCERNS");
            Utility.printCentered("Page " + (page + 1) + " of " + totalPages);
            Utility.printCentered("");
            
            int start = page * pageSize;
            int end = Math.min(start + pageSize, studentGrievances.size());
            
            for (int i = start; i < end; i++) {
                studentGrievances.get(i).display();
                System.out.println();
            }
            
            if (page < totalPages - 1) {
                Utility.waitForEnter("Press Enter for next page...");
            } else {
                Utility.waitForEnter("Press Enter to continue...");
            }
        }
    }
    
    // ========================= PRIVATE HELPER METHODS =========================
    
    private boolean isDuplicateStudent(ArrayList<Student> students, String srCode) {
        for (Student student : students) {
            if (student.getSrCode().equals(srCode)) {
                return true;
            }
        }
        return false;
    }
    
    private Student findStudentBySrCode(ArrayList<Student> students, String srCode) {
        for (Student student : students) {
            if (student.getSrCode().equals(srCode)) {
                return student;
            }
        }
        return null;
    }
}
