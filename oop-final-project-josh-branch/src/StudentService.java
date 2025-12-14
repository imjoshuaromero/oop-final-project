import java.util.ArrayList;

// StudentService: Handles all student-related operations
public class StudentService {
    
    // ========================= REGISTRATION =========================
    
    public static void registerStudent(ArrayList<Student> students) {
        String srCode = Utility.promptCenteredString("\nEnter SR-Code (format ##-#####):").trim();

        if (!AuthService.isValidSrCodeFormat(srCode)) {
            UIHelper.showError("Invalid SR-Code Format!\nFormat must be ##-##### (e.g., 24-31688)");
            return;
        }

        if (AuthService.isDuplicateStudent(students, srCode)) {
            UIHelper.showError("SR-Code Already Registered!\nThis SR-Code is already in the system.");
            return;
        }

        if (!StudentValidator.isCampusStudent(srCode)) {
            UIHelper.showError("SR-Code Not Found In Campus Registry!\nRegistration Denied.");
            return;
        }

        String name = Utility.promptCenteredString("Enter Full Name:").trim();
        String email = Utility.promptCenteredString("Enter Email:").trim();
        String password = Utility.promptCenteredString("Create Password:").trim();

        if (!StudentValidator.isValidEmail(email)) {
            UIHelper.showError("Invalid Email Format!\nPlease enter a valid email address.");
            return;
        }

        Utility.loadingBar("Registering", 600, 10);
        createAndSaveStudent(students, srCode, name, email, password);
        UIHelper.showSuccess("Registration Successful!\nYou Can Now Log In.");
    }
    
    private static void createAndSaveStudent(ArrayList<Student> students, String srCode, String name, String email, String password) {
        String hashed = PasswordUtils.hash(password);
        Student newStudent = new Student(srCode, name, email, hashed);
        students.add(newStudent);
        DataManager.saveStudents(students);
    }
    
    // ========================= VALIDATION =========================
    
    public static void validateRegisteredStudents(ArrayList<Student> students) {
        Utility.clearScreen();
        if (students.isEmpty()) {
            Utility.printCentered("");
            Utility.printCentered("No Registered Students To Validate.");
            Utility.printCentered("");
            Utility.waitForEnter("Press Enter to continue...");
            return;
        }

        // Reload registry to ensure latest data
        StudentValidator.reloadRegistry();
        
        int validCount = 0;
        int invalidSr = 0;
        int invalidEmail = 0;

        Utility.printTitle("Registered Students Validation Report");
        for (Student s : students) {
            boolean srOk = StudentValidator.isCampusStudent(s.getSrCode());
            boolean emailOk = StudentValidator.isValidEmail(s.getEmail());

            System.out.println("Student: " + s.getName() + " (" + s.getSrCode() + ")");
            System.out.println("  SR valid: " + srOk + ", Email valid: " + emailOk);

            if (srOk && emailOk) validCount++;
            if (!srOk) invalidSr++;
            if (!emailOk) invalidEmail++;
        }

        Utility.printCentered("Summary:");
        Utility.printCentered("Total Registered: " + students.size());
        Utility.printCentered("Fully Valid: " + validCount);
        Utility.printCentered("Invalid SR-Code: " + invalidSr);
        Utility.printCentered("Invalid Email: " + invalidEmail);
        Utility.printCentered("Registry Size: " + StudentValidator.registrySize());
        Utility.printCentered("===========================================");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    // ========================= GRIEVANCE OPERATIONS =========================
    
    public static void fileConcern(Student student, ArrayList<Grievance> grievances) {
        Utility.clearScreen();
        Utility.printTitle("Categories");
        Utility.printMenu(new String[]{
            "",
            "[1] Academic / Akademiko",
            "[2] Administrative Decisions / Desisyong Administratibo",
            "[3] Service and Facilities / Serbisyo at Pasilidad",
            "[4] Harrassment / Panghaharas",
            "[5] Others",
            ""
        });
        
        int cat = Utility.promptCenteredInt("Choose category (1-5):");
        
        if (cat < 1 || cat > 5) {
            UIHelper.showError("Invalid Category Choice!\nPlease select a number between 1 and 5.");
            return;
        }
        
        String category = getCategoryName(cat);
        String title = Utility.promptCenteredString("Enter Concern Title:").trim();
        String desc = Utility.promptCenteredString("Describe your concern (Magbigay ng maikling paglalarawan tungkol sa iyong pangunahing concern o reklamo):").trim();

        // Add and save grievance
        grievances.add(new Grievance(student.getSrCode(), title, category, desc));
        DataManager.saveGrievances(grievances);
        UIHelper.showSuccess("Concern Submitted Successfully!");
    }
    
    private static String getCategoryName(int category) {
        switch (category) {
            case 1: return "Academic / Akademiko";
            case 2: return "Administrative Decisions / Desisyong Administratibo";
            case 3: return "Service and Facilities / Serbisyo at Pasilidad";
            case 4: return "Harrassment / Panghaharas";
            default: return "Others";
        }
    }
    
    public static void viewMyConcerns(Student student, ArrayList<Grievance> grievances) {
        Utility.clearScreen();
        Utility.printTitle("MY CONCERNS");
        
        boolean found = false;
        for (Grievance grievance : grievances) {
            if (grievance.getStudentSr().equals(student.getSrCode())) {
                grievance.display();
                found = true;
            }
        }
        
        if (!found) {
            Utility.printCentered("");
            Utility.printCentered("No concerns found.");
            Utility.printCentered("");
        }
        
        Utility.waitForEnter("Press Enter to continue...");
    }
}
