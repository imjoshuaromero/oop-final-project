// main class ng system
import java.util.Scanner;

public class Main {
    // static para magamit sa lahat ng methods, scanner ito para sa input
    static Scanner sc = new Scanner(System.in);
    
    // array para sa students, max 10 students
    static Student[] students = new Student[10];
    
    // array naman para sa grievances, max 50
    static Grievance[] grievances = new Grievance[50];
    
    // counter para malaman ilang students na ang naka-register
    static int studentCount = 0;
    
    // counter naman para sa bilang ng grievances
    static int grievanceCount = 0;

    // main method, dito nagsisimula yung program
    public static void main(String[] args) {
        int choice;
        // do-while loop para mag-repeat yung menu hanggang mag-exit
        do {
            // i-display yung main menu
            System.out.println("\n===== GRIEVANCE REPORTING AND TRACKING SYSTEM =====");
            System.out.println("[1] Student Login");
            System.out.println("[2] Register Student");
            System.out.println("[3] Admin Login");
            System.out.println("[4] Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();  // kunin yung choice ng user
            sc.nextLine();  // clear yung buffer


            // switch para sa menu selection
            switch (choice) {
                case 1:
                    studentLogin();  // pag pinili student login
                    break;
                case 2:
                    registerStudent();  // pag mag-reregister
                    break;
                case 3:
                    adminLogin();  // pag admin login
                    break;
                case 4:
                    System.out.println("Thank you for using the system!");  // pag exit
                    break;
                default:
                    System.out.println("Invalid choice!");  // pag invalid yung input
            }
        } while (choice != 4);  // ulitin hanggang mag-exit (choice 4)
    }

    // method para mag-register ng student
    static void registerStudent() {
        // check muna kung puno na yung storage
        if (studentCount >= students.length) {
            System.out.println("Student storage full!");
            return;  // lumabas na kung puno na
        }

        // kunin yung SR-Code ng student
        System.out.print("\nEnter SR-Code (format ##-#####): ");
        String srCode = sc.nextLine().trim();  // trim para tanggalin extra spaces

        // check kung tama yung format ng SR-Code (##-#####)
        if (!srCode.matches("\\d{2}-\\d{5}")) {
            System.out.println("Invalid SR-Code format. Must be ##-#####.");
            return;  // lumabas kung mali format
        }

        // check kung naka-register na yung SR-Code
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].getSrCode().equals(srCode)) {
                System.out.println("SR-Code already registered!");
                return;  // lumabas kung existing na
            }
        }

        // kunin yung ibang info ng student
        System.out.print("Enter Full Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Create Password: ");
        String password = sc.nextLine().trim();

        // gumawa ng bagong Student object at i-save sa array
        students[studentCount] = new Student(srCode, name, password);
        studentCount++;  // dagdagan yung counter
        System.out.println("Registration successful! You can now log in.");
    }


    // method para sa student login
    static void studentLogin() {
        // kunin yung credentials ng student
        System.out.print("Enter SR Code: ");
        String sr = sc.nextLine().trim();
        System.out.print("Enter Password: ");
        String pw = sc.nextLine().trim();

        // variable para sa naka-login na student, null muna sa simula
        Student loggedIn = null;
        
        // hanapin yung student sa array
        for (int i = 0; i < studentCount; i++) {
            // check kung match yung SR code at password
            if (students[i] != null && students[i].getSrCode().equals(sr) && students[i].getPassword().equals(pw)) {
                loggedIn = students[i];  // i-save yung student
                break;  // stop na sa paghahanap
            }
        }

        // check kung may nakitang match
        if (loggedIn != null) {
            studentMenu(loggedIn);  // pumunta sa student menu
        } else {
            System.out.println("Invalid login credentials!");  // mali yung credentials
        }
    }

    // student dashboard/menu
    static void studentMenu(Student s) {
        int choice;
        // loop para mag-repeat yung menu
        do {
            // i-display yung student menu
            System.out.println("\n===== STUDENT DASHBOARD =====");
            System.out.println("Welcome, " + s.getName());  // i-welcome yung student
            System.out.println("[1] File a Concern");
            System.out.println("[2] View My Concerns");
            System.out.println("[3] Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // clear buffer

            // switch para sa menu options
            switch (choice) {
                case 1:
                    fileConcern(s);  // mag-file ng concern
                    break;
                case 2:
                    viewMyConcerns(s);  // tingnan yung mga concerns
                    break;
                case 3:
                    System.out.println("Logging out...");  // logout
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);  // ulitin hanggang mag-logout
    }


    // method para mag-file ng concern
    static void fileConcern(Student s) {
        // check muna kung puno na yung storage ng grievances
        if (grievanceCount >= grievances.length) {
            System.out.println("Grievance storage full!");
            return;
        }

        // i-display yung categories ng concerns
        System.out.println("\nCategories:");
        System.out.println("[1] Academic / Akademiko");
        System.out.println("[2] Administrative Decisions / Desisyong Administratibo");
        System.out.println("[3] Service and Facilities / Serbisyo at Pasilidad");
        System.out.println("[4] Harrassment / Panghaharas");
        System.out.println("[5] Others");
        System.out.print("Choose category (1-5): ");
        int cat = sc.nextInt();
        sc.nextLine();  // clear buffer

        // i-convert yung number choice sa category name
        String category;
        switch (cat) {
            case 1:
                category = "Academic / Akademiko";
                break;
            case 2:
                category = "Administrative Decisions / Desisyong Administratibo";
                break;
            case 3:
                category = "Service and Facilities / Serbisyo at Pasilidad";
                break;
            case 4:
                category = "Harrassment / Panghaharas";
                break;
            default:
                category = "Others";
                break;
        }

        // kunin yung title at description ng concern
        System.out.print("Enter Concern Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Describe your concern (Magbigay ng maikling paglalarawan tungkol sa iyong pangunahing concern o reklamo.: ");
        String desc = sc.nextLine().trim();

        // gumawa ng bagong Grievance at i-save sa array
        grievances[grievanceCount] = new Grievance(s.getSrCode(), title, category, desc);
        grievanceCount++;  // dagdagan yung counter
        System.out.println("Concern submitted successfully!");
    }


    // method para makita ng student yung mga concerns niya
    static void viewMyConcerns(Student s) {
        boolean found = false;  // tracker kung may nakita
        
        // i-loop lahat ng grievances
        for (int i = 0; i < grievanceCount; i++) {
            // check kung yung grievance ay sa student na ito
            if (grievances[i] != null && grievances[i].getStudentSR().equals(s.getSrCode())) {
                grievances[i].display();  // i-display yung grievance
                found = true;  // may nakita
            }
        }
        
        // kung wala namang nakita
        if (!found) System.out.println("No concerns found.");
    }

    // method para sa admin login
    static void adminLogin() {
        // kunin yung admin credentials
        System.out.print("Enter admin username: ");
        String user = sc.nextLine().trim();
        System.out.print("Enter password: ");
        String pass = sc.nextLine().trim();

        // check kung tama yung username at password (both "admin")
        if (user.equals("admin") && pass.equals("admin")) {
            Admin admin = new Admin();  // gumawa ng Admin object
            adminMenu(admin);  // pumunta sa admin menu
        } else {
            System.out.println("Invalid admin credentials!");  // mali yung credentials
        }
    }

    // admin dashboard/menu
    static void adminMenu(Admin admin) {
        int choice;
        // loop para mag-repeat yung menu
        do {
            // i-display yung admin menu
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("[1] View All Concerns");
            System.out.println("[2] Update Concerns");
            System.out.println("[3] Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // clear buffer

            // switch para sa menu options
            switch (choice) {
                case 1:
                    admin.viewAllConcerns(grievances, grievanceCount);  // tingnan lahat ng concerns
                    break;
                case 2:
                    admin.updateConcern(grievances, grievanceCount);  // i-update yung concern
                    break;
                case 3:
                    System.out.println("Logging out...");  // logout
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);  // ulitin hanggang mag-logout
    }
}
