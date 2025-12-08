import java.util.ArrayList;

/**
 * MenuHandler: Manages all menu operations and navigation
 * Uses dependency injection for loose coupling
 */
public class MenuHandler {
    private final IStudentOperations studentOps;
    private final IAdminOperations adminOps;
    private final IAuthenticationService authService;
    private final IDataPersistence dataService;
    
    // Constructor injection for interface dependencies
    public MenuHandler(IStudentOperations studentOps, 
                      IAdminOperations adminOps,
                      IAuthenticationService authService,
                      IDataPersistence dataService) {
        this.studentOps = studentOps;
        this.adminOps = adminOps;
        this.authService = authService;
        this.dataService = dataService;
    }
    
    public void runMainMenu(ArrayList<Student> students, ArrayList<Grievance> grievances) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("SSC STUDENT WELFARE SYSTEM");
            Utility.printMenu(new String[]{
                "",
                "[1] Student Login",
                "[2] Register Student",
                "[3] Admin Login",
                "[4] Exit",
                ""
            });
            choice = Utility.promptCenteredInt("Enter choice:");

            switch (choice) {
                case 1: 
                    studentLogin(students, grievances); 
                    break;
                case 2: 
                    studentOps.registerStudent(students); 
                    break;
                case 3: 
                    adminLogin(students, grievances); 
                    break;
                case 4: 
                    UIHelper.exitProgram(); 
                    break;
                default: 
                    UIHelper.showInvalidChoice(1, 4);
            }
        } while (choice != 4);
    }

    // ========================= STUDENT OPERATIONS =========================
    
    private void studentLogin(ArrayList<Student> students, ArrayList<Grievance> grievances) {
        String sr = Utility.promptCenteredString("Enter SR Code:").trim();
        String pw = Utility.promptCenteredString("Enter Password:").trim();
        Utility.spinner("Logging In", 800);

        Student loggedIn = authService.authenticateStudent(students, sr, pw);

        if (loggedIn != null) {
            UIHelper.showLoginSuccess(loggedIn.getName());
            studentMenu(loggedIn, students, grievances);
        } else {
            UIHelper.showError("Invalid Login Credentials!\nPlease check your SR Code and Password.");
        }
    }

    private void studentMenu(Student s, ArrayList<Student> students, ArrayList<Grievance> grievances) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("STUDENT DASHBOARD");
            Utility.printCentered("Welcome, " + s.getName());
            Utility.printMenu(new String[]{
                "",
                "[1] File a Concern",
                "[2] View My Concerns",
                "[3] Change Password",
                "[4] Logout",
                ""
            });
            choice = Utility.promptCenteredInt("Enter choice:");

            switch (choice) {
                case 1:
                    studentOps.fileConcern(s, grievances);
                    break;
                case 2:
                    studentOps.viewStudentConcerns(s, grievances);
                    break;
                case 3:
                    changePassword(s, students);
                    break;
                case 4:
                    Utility.overlaySpinner("Logging Out", 1000);
                    break;
                default:
                    UIHelper.showInvalidChoice(1, 4);
            }
        } while (choice != 4);
    }

    private void changePassword(Student s, ArrayList<Student> students) {
        String current = Utility.promptCenteredString("Enter Current Password:").trim();
        String newPassword = Utility.promptCenteredString("Enter New Password:").trim();
        String confirmPassword = Utility.promptCenteredString("Confirm New Password:").trim();
        
        if (authService.changePassword(s, current, newPassword, confirmPassword)) {
            dataService.saveStudents(students);
            UIHelper.showSuccess("Password Changed Successfully!");
        }
    }

    // ========================= ADMIN OPERATIONS =========================

    private void adminLogin(ArrayList<Student> students, ArrayList<Grievance> grievances) {
        String user = Utility.promptCenteredString("Enter Admin Username:").trim();
        String pass = Utility.promptCenteredString("Enter Password:").trim();
        Utility.spinner("Checking Admin Credentials", 700);

        if (authService.authenticateAdmin(user, pass)) {
            UIHelper.showLoginSuccess("Administrator");
            adminMenu(new Admin(), students, grievances);
        } else {
            UIHelper.showError("Invalid Admin Credentials!\nAccess Denied.");
        }
    }

    private void adminMenu(Admin admin, ArrayList<Student> students, ArrayList<Grievance> grievances) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("ADMIN DASHBOARD");
            Utility.printMenu(new String[]{
                "",
                "[1] View All Concerns",
                "[2] Update Concerns",
                "[3] Delete Concern",
                "[4] Validate Registered Students",
                "[5] Reload SR Registry",
                "[6] Reset Student Password",
                "[7] Logout",
                ""
            });
            choice = Utility.promptCenteredInt("Enter choice:");

            switch (choice) {
                case 1:
                    adminOps.viewAllConcerns(grievances);
                    break;
                case 2:
                    adminOps.updateConcern(grievances);
                    break;
                case 3:
                    adminOps.deleteConcern(grievances);
                    break;
                case 4:
                    studentOps.validateRegisteredStudents(students);
                    break;
                case 5:
                    StudentValidator.reloadRegistry();
                    UIHelper.showSuccess("SR Registry Reloaded!\nTotal Entries: " + StudentValidator.registrySize());
                    break;
                case 6:
                    String target = Utility.promptCenteredString("Enter SR-Code to reset password:").trim();
                    authService.resetStudentPassword(students, target);
                    break;
                case 7:
                    Utility.overlaySpinner("Logging Out", 1000);
                    break;
                default:
                    UIHelper.showInvalidChoice(1, 7);
            }
        } while (choice != 7);
    }
}
