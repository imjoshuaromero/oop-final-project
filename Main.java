import java.util.Scanner;


public class Main {
    static Scanner sc = new Scanner(System.in);
    static Student[] students = new Student[10];
    static Grievance[] grievances = new Grievance[50];
    static int studentCount = 0;
    static int grievanceCount = 0;


    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== GRIEVANCE REPORTING AND TRACKING SYSTEM =====");
            System.out.println("[1] Student Login");
            System.out.println("[2] Register Student");
            System.out.println("[3] Admin Login");
            System.out.println("[4] Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1:
                    studentLogin();
                    break;
                case 2:
                    registerStudent();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }


    // STUDENT REGISTRATION
    static void registerStudent() {
        if (studentCount >= students.length) {
            System.out.println("Student storage full!");
            return;
        }


        System.out.print("\nEnter SR-Code (format ##-#####): ");
        String srCode = sc.nextLine().trim();


        if (!srCode.matches("\\d{2}-\\d{5}")) {
            System.out.println("Invalid SR-Code format. Must be ##-#####.");
            return;
        }


        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].getSrCode().equals(srCode)) {
                System.out.println("SR-Code already registered!");
                return;
            }
        }


        System.out.print("Enter Full Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter Email: "); //dapat may @g.batstate-u.edu.ph
        String email = sc.nextLine().trim();
        System.out.print("Create Password: "); // change into ***
        String password = sc.nextLine().trim();


        students[studentCount] = new Student(srCode, name, password);
        studentCount++;
        System.out.println("Registration successful! You can now log in.");
    }


    // STUDENT LOGIN
    static void studentLogin() {
        System.out.print("Enter SR Code: ");
        String sr = sc.nextLine().trim();
        System.out.print("Enter Password: "); //change into ****
        String pw = sc.nextLine().trim();


        Student loggedIn = null;
        for (int i = 0; i < studentCount; i++) {
            if (students[i] != null && students[i].getSrCode().equals(sr) && students[i].getPassword().equals(pw)) {
                loggedIn = students[i];
                break;
            }
        }


        if (loggedIn != null) {
            studentMenu(loggedIn);
        } else {
            System.out.println("Invalid login credentials!");
        }
    }


    // STUDENT DASHBOARD
    static void studentMenu(Student s) {
        int choice;
        do {
            System.out.println("\n===== STUDENT DASHBOARD =====");
            System.out.println("Welcome, " + s.getName());
            System.out.println("[1] File a Concern");
            System.out.println("[2] View My Concerns");
            System.out.println("[3] Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1:
                    fileConcern(s);
                    break;
                case 2:
                    viewMyConcerns(s);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }


    // FILE CONCERN
    static void fileConcern(Student s) {
        if (grievanceCount >= grievances.length) {
            System.out.println("Grievance storage full!");
            return;
        }


        System.out.println("\nCategories:");
        System.out.println("[1] Academic / Akademiko");
        System.out.println("[2] Administrative Decisions / Desisyong Administratibo");
        System.out.println("[3] Service and Facilities / Serbisyo at Pasilidad");
        System.out.println("[4] Harrassment / Panghaharas");
        System.out.println("[5] Others");
        System.out.print("Choose category (1-5): ");
        int cat = sc.nextInt();
        sc.nextLine();


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


        System.out.print("Enter Concern Title: ");
        String title = sc.nextLine().trim();
        System.out.print("Describe your concern (Magbigay ng maikling paglalarawan tungkol sa iyong pangunahing concern o reklamo.: ");
        String desc = sc.nextLine().trim();


        // assign then increment to keep consistency
        grievances[grievanceCount] = new Grievance(s.getSrCode(), title, category, desc);
        grievanceCount++;
        System.out.println("Concern submitted successfully!");
    }


    // VIEW STUDENT'S CONCERNS
    static void viewMyConcerns(Student s) {
        boolean found = false;
        for (int i = 0; i < grievanceCount; i++) {
            if (grievances[i] != null && grievances[i].getStudentSR().equals(s.getSrCode())) {
                grievances[i].display();
                found = true;
            }
        }
        if (!found) System.out.println("No concerns found.");
    }


    // ADMIN LOGIN
    static void adminLogin() {
        System.out.print("Enter admin username: ");
        String user = sc.nextLine().trim();
        System.out.print("Enter password: "); // lalagyan ng ****
        String pass = sc.nextLine().trim();


        if (user.equals("admin") && pass.equals("admin")) { //hardcoded as admin
            Admin admin = new Admin();
            adminMenu(admin);
        } else {
            System.out.println("Invalid admin credentials!");
        }
    }


    // ADMIN DASHBOARD
    static void adminMenu(Admin admin) {
        int choice;
        do {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("[1] View All Concerns");
            System.out.println("[2] Update Concerns");
            System.out.println("[3] Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();


            switch (choice) {
                case 1:
                    admin.viewAllConcerns(grievances, grievanceCount);
                    break;
                case 2:
                    admin.updateConcern(grievances, grievanceCount);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }
}
