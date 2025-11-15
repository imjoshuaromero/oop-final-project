import java.util.ArrayList;


public class Main {
    // scanner moved to Utility.java to avoid duplication across classes
    static ArrayList<Student> students = new ArrayList<>();
    static ArrayList<Grievance> grievances = new ArrayList<>();


    public static void main(String[] args) {
    // load pre-defined students mula sa `students.txt` (default password = sr-code)
    // tagalog note: binabasa dito ang file para i-preload ang mga estudyante. kung
    // may binago ka sa `students.txt` bago patakbuhin ang program, dito ito makukuha.
        loadStudentsFromFile();
        loadGrievancesFromFile();
        // siguraduhing nire-reload din ng validator ang registry mula sa students.json
        StudentValidator.reloadRegistry();
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("SSC STUDENT WELFARE SYSTEM");
            Utility.printMenu(new String[]{"","[1] Student Login","[2] Register Student","[3] Admin Login","[4] Exit",""});
            choice = Utility.promptCenteredInt("Enter choice:");


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
                    Utility.overlaySpinner("Exiting Program", 1000);
                    Utility.clearScreen();
                    Utility.printCentered("");
                    Utility.printCentered("Thank You For Using The System!");
                    Utility.printCentered("");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    // basahin ang students mula sa `students.txt` sa project root.
    // format ng lines: sr|name  o  sr|name|email|password
    // tagalog note: ang function na ito ay nagse-setup ng initial na listahan ng students
    // para makapag-login agad ang mga preloaded na estudyante.
    static void loadStudentsFromFile() {
        java.io.File f = new java.io.File("students.json");
        if (!f.exists()) {
            System.out.println("No students.json file found; skipping preload.");
            return;
        }

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            // find each json object (basic split by braces) and extract fields regardless of order
            java.util.regex.Pattern objPattern = java.util.regex.Pattern.compile("\\{(.*?)\\}", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher objMatcher = objPattern.matcher(content);
            while (objMatcher.find()) {
                String objText = objMatcher.group(1);
                String sr = extractJsonField(objText, "sr");
                String name = extractJsonField(objText, "name");
                String email = extractJsonField(objText, "email");
                String pwHash = extractJsonField(objText, "passwordHash");

                if (sr == null || !sr.matches("\\d{2}-\\d{5}")) continue;
                if (name == null) name = "";
                if (email == null) email = "";
                if (pwHash == null) pwHash = "";

                boolean exists = false;
                for (Student s : students) if (s.getSrCode().equals(sr)) { exists = true; break; }
                if (exists) continue;

                students.add(new Student(sr, name, email, pwHash));
            }
        } catch (Exception e) {
            System.out.println("Error loading students.json: " + e.getMessage());
        }
    }

    // helper: extract a json string field value from inside an object text (not full json parse)
    static String extractJsonField(String objText, String field) {
        try {
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + java.util.regex.Pattern.quote(field) + "\"\\s*:\\s*\"(.*?)\"", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher m = p.matcher(objText);
            if (m.find()) return m.group(1);
        } catch (Exception e) {
            // ignore
        }
        return null;
    }

    // write entire students list to students.json (overwrites file)
    static void writeAllStudentsToFile() {
        java.io.File f = new java.io.File("students.json");
        try (java.io.FileWriter fw = new java.io.FileWriter(f, false)) {
            fw.write("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                String obj = "{\"sr\":\"" + escapeJson(s.getSrCode()) + "\"," +
                             "\"name\":\"" + escapeJson(s.getName()) + "\"," +
                             "\"email\":\"" + escapeJson(s.getEmail()) + "\"," +
                             "\"passwordHash\":\"" + escapeJson(s.getPassword()) + "\"}";
                fw.write(obj);
                if (i < students.size() - 1) fw.write(",\n");
            }
            fw.write("]");
        } catch (Exception e) {
            System.out.println("Error writing students.json: " + e.getMessage());
        }
    }

    // escape characters for json string values (very small helper)
    static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }

    // load grievances from grievances.json
    static void loadGrievancesFromFile() {
        java.io.File f = new java.io.File("grievances.json");
        if (!f.exists()) {
            return; // no file yet, that's okay
        }

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            // find each json object and extract fields
            java.util.regex.Pattern objPattern = java.util.regex.Pattern.compile("\\{(.*?)\\}", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher objMatcher = objPattern.matcher(content);
            while (objMatcher.find()) {
                String objText = objMatcher.group(1);
                String studentSR = extractJsonField(objText, "studentSR");
                String title = extractJsonField(objText, "title");
                String category = extractJsonField(objText, "category");
                String description = extractJsonField(objText, "description");
                String status = extractJsonField(objText, "status");
                String feedback = extractJsonField(objText, "feedback");

                if (studentSR == null || title == null) continue;

                // create grievance and restore its status/feedback
                Grievance g = new Grievance(studentSR, title, category != null ? category : "", description != null ? description : "");
                if (status != null) g.setStatus(status);
                if (feedback != null) g.setFeedback(feedback);
                grievances.add(g);
            }
        } catch (Exception e) {
            System.out.println("Error loading grievances.json: " + e.getMessage());
        }
    }

    // write all grievances to grievances.json
    static void writeGrievancesToFile() {
        java.io.File f = new java.io.File("grievances.json");
        try (java.io.FileWriter fw = new java.io.FileWriter(f, false)) {
            fw.write("[");
            for (int i = 0; i < grievances.size(); i++) {
                Grievance g = grievances.get(i);
                String obj = "{\"id\":" + g.getId() + "," +
                             "\"studentSR\":\"" + escapeJson(g.getStudentSR()) + "\"," +
                             "\"title\":\"" + escapeJson(g.getTitle()) + "\"," +
                             "\"category\":\"" + escapeJson(g.getCategory()) + "\"," +
                             "\"description\":\"" + escapeJson(g.getDescription()) + "\"," +
                             "\"status\":\"" + escapeJson(g.getStatus()) + "\"," +
                             "\"feedback\":\"" + escapeJson(g.getFeedback()) + "\"}";
                fw.write(obj);
                if (i < grievances.size() - 1) fw.write(",\n");
            }
            fw.write("]");
        } catch (Exception e) {
            System.out.println("Error writing grievances.json: " + e.getMessage());
        }
    }

    // i-append ang bagong registradong estudyante sa `students.txt`
    // format na sinusulat: sr|name|email|password
    // tagalog note: ginagamit ito para mag-save ng bagong registrasyon nang permanente
    // para manatili ang data kahit i-restart ang program.
    static void appendStudentToFile(Student s) {
        java.io.File f = new java.io.File("students.txt");
        try (java.io.FileWriter fw = new java.io.FileWriter(f, true)) {
            String line = s.getSrCode() + "|" + s.getName() + "|" + s.getEmail() + "|" + s.getPassword() + System.lineSeparator();
            fw.write(line);
        } catch (Exception e) {
            System.out.println("Error appending to students.txt: " + e.getMessage());
        }
    }

    // i-update ang umiiral na linya ng estudyante sa `students.txt` (i-match gamit ang sr-code)
    // tagalog note: kapag nagpalit ng password ang student, tatawagin ito para palitan
    // ang record sa file para manatiling up-to-date ang storage.
    static void updateStudentInFile(Student s) {
        java.io.File f = new java.io.File("students.txt");
        if (!f.exists()) return;
        try {
            java.util.List<String> all = new java.util.ArrayList<>();
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String trimmed = line.trim();
                    if (trimmed.isEmpty()) continue;
                    String sr = trimmed.split("\\|", 2)[0].trim();
                    if (sr.equals(s.getSrCode())) {
                        // replace with updated record
                        String newline = s.getSrCode() + "|" + s.getName() + "|" + s.getEmail() + "|" + s.getPassword();
                        all.add(newline);
                    } else {
                        all.add(line);
                    }
                }
            }

            try (java.io.FileWriter fw = new java.io.FileWriter(f, false)) {
                for (String out : all) fw.write(out + System.lineSeparator());
            }
        } catch (Exception e) {
            System.out.println("Error updating students.txt: " + e.getMessage());
        }
    }


    // student registration
    static void registerStudent() {
    String srCode = Utility.promptCenteredString("\nEnter SR-Code (format ##-#####):").trim();


        if (!srCode.matches("\\d{2}-\\d{5}")) {
            System.out.println("Invalid SR-Code format. Must be ##-#####.");
            return;
        }


    // suriin muna kung duplicate na ang sr-code sa in-memory list
        for (Student student : students) {
                if (student.getSrCode().equals(srCode)) {
                System.out.println("SR-Code already registered!");
                return;
            }
        }

    // tagalog: i-verify ang sr-code laban sa campus registry (students.txt)
    // kung wala sa registry, hindi papayagan mag-register.
        if (!StudentValidator.isCampusStudent(srCode)) {
            System.out.println("SR-Code not found in campus registry. Registration denied.");
            return;
        }


    String name = Utility.promptCenteredString("Enter Full Name:").trim();
    String email = Utility.promptCenteredString("Enter Email:").trim();
    String password = Utility.promptCenteredString("Create Password:").trim();

        // tagalog: i-validate muna ang email format bago gumawa ng account
        if (!StudentValidator.isValidEmail(email)) {
            System.out.println("Invalid email format. Registration failed.");
            return;
        }

        // gumawa ng bagong student object at idagdag sa memory
        // we store the hashed password in the student object (not the plain text)
        // show a small loading bar during registration to feel responsive
    Utility.loadingBar("Registering", 600, 10);
        String hashed = PasswordUtils.hash(password);
        Student newStudent = new Student(srCode, name, email, hashed);
        students.add(newStudent);
        // isulat/persist ang buong list ng students sa students.json para manatili ang registration
        writeAllStudentsToFile();
        Utility.printCentered("Registration successful! You can now log in.");
    }

    // admin: i-validate ang lahat ng nakarehistrong estudyante laban sa campus registry at email format
    // admin: i-validate ang lahat ng nakarehistrong estudyante laban sa registry at email format
    // tagalog note: pwede gamitin ng admin para makita kung ilan ang valid o kung may mali
    // sa mga sr-code / email ng mga nakarehistrong estudyante.
    static void validateRegisteredStudents() {
        Utility.clearScreen();
        if (students.isEmpty()) {
            System.out.println("No registered students to validate.");
            return;
        }

        // reload the registry first to ensure we have the latest data
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


    // student login
    static void studentLogin() {
    String sr = Utility.promptCenteredString("Enter SR Code:").trim();
    String pw = Utility.promptCenteredString("Enter Password:").trim();

        // spinner while checking credentials
        Utility.spinner("Logging in...", 800);

        Student loggedIn = null;
        for (Student student : students) {
            // compare hashed password: hash the entered password and compare with stored hash
            if (student.getSrCode().equals(sr) && student.getPassword() != null && student.getPassword().equals(PasswordUtils.hash(pw))) {
                loggedIn = student;
                break;
            }
        }

        if (loggedIn != null) {
            Utility.printCentered("Login successful — Welcome, " + loggedIn.getName());
            studentMenu(loggedIn);
        } else {
            Utility.printCentered("Invalid login credentials!");
        }
    }


    // student dashboard
    static void studentMenu(Student s) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("STUDENT DASHBOARD");
            Utility.printCentered("Welcome, " + s.getName());
            Utility.printMenu(new String[]{"","[1] File a Concern","[2] View My Concerns","[3] Change Password","[4] Logout",""});
            choice = Utility.promptCenteredInt("Enter choice:");


            switch (choice) {
                case 1:
                    fileConcern(s);
                    break;
                case 2:
                    viewMyConcerns(s);
                    break;
                case 3:
                    // tagalog: simula ng proseso para magpalit ng password.
                    // una, hihingin ang kasalukuyang password para i-verify ang user.
                    String current = Utility.promptCenteredString("Enter current password:").trim();
                    if (!s.getPassword().equals(PasswordUtils.hash(current))) {
                        System.out.println("Current password incorrect.");
                        break;
                    }
                    String np = Utility.promptCenteredString("Enter new password:").trim();
                    String cp = Utility.promptCenteredString("Confirm new password:").trim();
                    if (!np.equals(cp)) {
                        System.out.println("New password and confirmation do not match.");
                        break;
                    }
                    if (np.isEmpty()) {
                        System.out.println("Password cannot be empty.");
                        break;
                    }
                    // set password to hashed new password
                    s.setPassword(PasswordUtils.hash(np));
                    // persist password change to file so it survives restarts
                    writeAllStudentsToFile();
                    System.out.println("Password changed successfully.");
                    break;
                case 4:
                    Utility.overlaySpinner("Logging Out", 1000);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }


    // file concern
    static void fileConcern(Student s) {
        Utility.clearScreen();
        Utility.printTitle("Categories");
        Utility.printMenu(new String[]{"","[1] Academic / Akademiko","[2] Administrative Decisions / Desisyong Administratibo","[3] Service and Facilities / Serbisyo at Pasilidad","[4] Harrassment / Panghaharas","[5] Others",""});
    int cat = Utility.promptCenteredInt("Choose category (1-5):");
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


    String title = Utility.promptCenteredString("Enter Concern Title:").trim();
    String desc = Utility.promptCenteredString("Describe your concern (Magbigay ng maikling paglalarawan tungkol sa iyong pangunahing concern o reklamo):").trim();


    // add grievance to arraylist
        grievances.add(new Grievance(s.getSrCode(), title, category, desc));
        writeGrievancesToFile(); // save to file
        System.out.println("Concern submitted successfully!");
    }


    // view student's concerns
    static void viewMyConcerns(Student s) {
        Utility.clearScreen();
        Utility.printTitle("MY CONCERNS");
        boolean found = false;
        for (Grievance grievance : grievances) {
            if (grievance.getStudentSR().equals(s.getSrCode())) {
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


    // admin login
    static void adminLogin() {
    String user = Utility.promptCenteredString("Enter admin username:").trim();
    String pass = Utility.promptCenteredString("Enter password:").trim();
        Utility.spinner("Checking admin credentials...", 700);

        if (user.equals("admin") && pass.equals("admin")) {
            Utility.printCentered("Admin authenticated");
            Admin admin = new Admin();
            adminMenu(admin);
        } else {
            Utility.printCentered("Invalid admin credentials!");
        }
    }


    // admin dashboard
    static void adminMenu(Admin admin) {
        int choice;
        do {
            Utility.clearScreen();
            Utility.printTitle("ADMIN DASHBOARD");
            Utility.printMenu(new String[]{"","[1] View All Concerns","[2] Update Concerns","[3] Delete Concern","[4] Validate Registered Students","[5] Reload SR Registry","[6] Reset Student Password","[7] Logout",""});
            choice = Utility.promptCenteredInt("Enter choice:");


            switch (choice) {
                case 1:
                    admin.viewAllConcerns(grievances);
                    break;
                case 2:
                    admin.updateConcern(grievances);
                    writeGrievancesToFile(); // save changes to file
                    break;
                case 3:
                    admin.deleteConcern(grievances);
                    writeGrievancesToFile(); // save changes to file
                    break;
                    case 4:
                        validateRegisteredStudents();
                        break;
                    case 5:
                        // reload the sr registry from students.json
                        StudentValidator.reloadRegistry();
                        System.out.println("SR registry reloaded. Entries: " + StudentValidator.registrySize());
                        break;
                    case 6:
                        // admin resets a student's password (sets to sr-code by default)
                        String target = Utility.promptCenteredString("Enter SR-Code to reset password:").trim();
                        Student found = null;
                        for (Student st : students) if (st.getSrCode().equals(target)) { found = st; break; }
                        if (found == null) {
                            System.out.println("Student not found.");
                        } else {
                            // reset to sr-code (hashed)
                            found.setPassword(PasswordUtils.hash(found.getSrCode()));
                            writeAllStudentsToFile();
                            System.out.println("Password reset to SR-Code for " + found.getSrCode());
                        }
                        break;
                    case 7:
                        Utility.overlaySpinner("Logging Out", 1000);
                        break;
                default:
                    System.out.println("Invalid choice!");
            }
            } while (choice != 7);
    }
}
