import java.util.ArrayList;

// DataManager: Handles all file I/O operations for students and grievances
public class DataManager {
    private static final String STUDENTS_FILE = "students.json";
    private static final String GRIEVANCES_FILE = "grievances.json";
    private static final String SR_CODE_PATTERN = "\\d{2}-\\d{5}";
    
    // Initialize and load all application data
    public static AppData loadApplicationData() {
        ArrayList<Student> students = loadStudents();
        ArrayList<Grievance> grievances = loadGrievances();
        StudentValidator.reloadRegistry();
        return new AppData(students, grievances);
    }
    
    // ========================= STUDENT FILE OPERATIONS =========================
    
    public static ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        java.io.File f = new java.io.File(STUDENTS_FILE);
        
        if (!f.exists()) {
            System.out.println("No " + STUDENTS_FILE + " file found; skipping preload.");
            return students;
        }

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), 
                                      java.nio.charset.StandardCharsets.UTF_8);
            java.util.regex.Pattern jsonPattern = java.util.regex.Pattern.compile(
                "\\{(.*?)\\}", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher jsonMatcher = jsonPattern.matcher(content);
            
            while (jsonMatcher.find()) {
                Student student = parseStudentFromJson(jsonMatcher.group(1));
                if (student != null && !isDuplicateStudent(students, student.getSrCode())) {
                    students.add(student);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading " + STUDENTS_FILE + ": " + e.getMessage());
        }
        
        return students;
    }
    
    private static Student parseStudentFromJson(String jsonText) {
        String sr = extractJsonField(jsonText, "sr");
        String name = extractJsonField(jsonText, "name");
        String email = extractJsonField(jsonText, "email");
        String passwordHash = extractJsonField(jsonText, "passwordHash");

        if (sr == null || !sr.matches(SR_CODE_PATTERN)) return null;
        
        return new Student(
            sr, 
            name != null ? name : "", 
            email != null ? email : "", 
            passwordHash != null ? passwordHash : ""
        );
    }
    
    private static boolean isDuplicateStudent(ArrayList<Student> students, String srCode) {
        for (Student s : students) {
            if (s.getSrCode().equals(srCode)) return true;
        }
        return false;
    }
    
    public static void saveStudents(ArrayList<Student> students) {
        java.io.File f = new java.io.File(STUDENTS_FILE);
        try (java.io.FileWriter fw = new java.io.FileWriter(f, false)) {
            fw.write("[");
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                fw.write(buildStudentJson(s));
                if (i < students.size() - 1) fw.write(",\n");
            }
            fw.write("]");
        } catch (Exception e) {
            System.out.println("Error writing " + STUDENTS_FILE + ": " + e.getMessage());
        }
    }
    
    private static String buildStudentJson(Student s) {
        return "{\"sr\":\"" + escapeJson(s.getSrCode()) + "\"," +
               "\"name\":\"" + escapeJson(s.getName()) + "\"," +
               "\"email\":\"" + escapeJson(s.getEmail()) + "\"," +
               "\"passwordHash\":\"" + escapeJson(s.getPassword()) + "\"}";
    }
    
    // ========================= GRIEVANCE FILE OPERATIONS =========================
    
    public static ArrayList<Grievance> loadGrievances() {
        ArrayList<Grievance> grievances = new ArrayList<>();
        java.io.File f = new java.io.File(GRIEVANCES_FILE);
        
        if (!f.exists()) return grievances;

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), 
                                      java.nio.charset.StandardCharsets.UTF_8);
            java.util.regex.Pattern jsonPattern = java.util.regex.Pattern.compile(
                "\\{(.*?)\\}", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher jsonMatcher = jsonPattern.matcher(content);
            
            while (jsonMatcher.find()) {
                Grievance grievance = parseGrievanceFromJson(jsonMatcher.group(1));
                if (grievance != null) {
                    grievances.add(grievance);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading " + GRIEVANCES_FILE + ": " + e.getMessage());
        }
        
        return grievances;
    }
    
    private static Grievance parseGrievanceFromJson(String jsonText) {
        String studentSr = extractJsonField(jsonText, "studentSR");
        String title = extractJsonField(jsonText, "title");
        String category = extractJsonField(jsonText, "category");
        String description = extractJsonField(jsonText, "description");
        String status = extractJsonField(jsonText, "status");
        String feedback = extractJsonField(jsonText, "feedback");

        if (studentSr == null || title == null) return null;

        Grievance g = new Grievance(
            studentSr, 
            title, 
            category != null ? category : "", 
            description != null ? description : ""
        );
        if (status != null) g.setStatus(status);
        if (feedback != null) g.setFeedback(feedback);
        return g;
    }
    
    public static void saveGrievances(ArrayList<Grievance> grievances) {
        java.io.File f = new java.io.File(GRIEVANCES_FILE);
        try (java.io.FileWriter fw = new java.io.FileWriter(f, false)) {
            fw.write("[");
            for (int i = 0; i < grievances.size(); i++) {
                Grievance g = grievances.get(i);
                fw.write(buildGrievanceJson(g));
                if (i < grievances.size() - 1) fw.write(",\n");
            }
            fw.write("]");
        } catch (Exception e) {
            System.out.println("Error writing " + GRIEVANCES_FILE + ": " + e.getMessage());
        }
    }
    
    private static String buildGrievanceJson(Grievance g) {
        return "{\"id\":" + g.getId() + "," +
               "\"studentSR\":\"" + escapeJson(g.getStudentSr()) + "\"," +
               "\"title\":\"" + escapeJson(g.getTitle()) + "\"," +
               "\"category\":\"" + escapeJson(g.getCategory()) + "\"," +
               "\"description\":\"" + escapeJson(g.getDescription()) + "\"," +
               "\"status\":\"" + escapeJson(g.getStatus()) + "\"," +
               "\"feedback\":\"" + escapeJson(g.getFeedback()) + "\"}";
    }
    
    // ========================= JSON HELPERS =========================
    
    private static String extractJsonField(String jsonText, String field) {
        try {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(
                "\"" + java.util.regex.Pattern.quote(field) + "\"\\s*:\\s*\"(.*?)\"", 
                java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher matcher = pattern.matcher(jsonText);
            if (matcher.find()) return matcher.group(1);
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
    
    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");
    }
}
