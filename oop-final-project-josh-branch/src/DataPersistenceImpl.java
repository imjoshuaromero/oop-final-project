import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Implementation of data persistence service
 * Handles file I/O operations for students and grievances
 */
public class DataPersistenceImpl implements IDataPersistence {
    private static final String STUDENTS_FILE = "students.json";
    private static final String GRIEVANCES_FILE = "grievances.json";
    
    @Override
    public AppData loadApplicationData() {
        ArrayList<Student> students = loadStudents();
        ArrayList<Grievance> grievances = loadGrievances();
        StudentValidator.reloadRegistry(); // Reload validation registry
        return new AppData(students, grievances);
    }
    
    @Override
    public void saveStudents(ArrayList<Student> students) {
        try (PrintWriter writer = new PrintWriter(STUDENTS_FILE, StandardCharsets.UTF_8)) {
            writer.println("[");
            
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                writer.print("{\"sr\":\"" + escapeJson(student.getSrCode()) + "\",");
                writer.print("\"name\":\"" + escapeJson(student.getName()) + "\",");
                writer.print("\"email\":\"" + escapeJson(student.getEmail()) + "\",");
                writer.print("\"passwordHash\":\"" + escapeJson(student.getPassword()) + "\"");
                
                if (i < students.size() - 1) {
                    writer.println("},");
                } else {
                    writer.println("}");
                }
            }
            
            writer.println("]");
        } catch (Exception e) {
            UIHelper.showError("Failed to save students: " + e.getMessage());
        }
    }
    
    @Override
    public void saveGrievances(ArrayList<Grievance> grievances) {
        try (PrintWriter writer = new PrintWriter(GRIEVANCES_FILE, StandardCharsets.UTF_8)) {
            writer.println("[");
            
            for (int i = 0; i < grievances.size(); i++) {
                Grievance grievance = grievances.get(i);
                writer.println("  {");
                writer.println("    \"grievanceId\": " + grievance.getId() + ",");
                writer.println("    \"studentSr\": \"" + escapeJson(grievance.getStudentSr()) + "\",");
                writer.println("    \"title\": \"" + escapeJson(grievance.getTitle()) + "\",");
                writer.println("    \"category\": \"" + escapeJson(grievance.getCategory()) + "\",");
                writer.println("    \"description\": \"" + escapeJson(grievance.getDescription()) + "\",");
                writer.println("    \"status\": \"" + escapeJson(grievance.getStatus()) + "\",");
                writer.println("    \"feedback\": \"" + escapeJson(grievance.getFeedback()) + "\"");
                
                if (i < grievances.size() - 1) {
                    writer.println("  },");
                } else {
                    writer.println("  }");
                }
            }
            
            writer.println("]");
        } catch (Exception e) {
            UIHelper.showError("Failed to save grievances: " + e.getMessage());
        }
    }
    
    // ========================= PRIVATE HELPER METHODS =========================
    
    private ArrayList<Student> loadStudents() {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File(STUDENTS_FILE);
        
        if (!file.exists()) {
            return students;
        }
        
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            Pattern jsonPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
            Matcher jsonMatcher = jsonPattern.matcher(content);
            
            while (jsonMatcher.find()) {
                Student student = parseStudentFromJson(jsonMatcher.group(1));
                if (student != null) {
                    students.add(student);
                }
            }
        } catch (Exception e) {
            UIHelper.showError("Failed to load students: " + e.getMessage());
        }
        
        return students;
    }
    
    private ArrayList<Grievance> loadGrievances() {
        ArrayList<Grievance> grievances = new ArrayList<>();
        File file = new File(GRIEVANCES_FILE);
        
        if (!file.exists()) {
            return grievances;
        }
        
        try {
            String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            Pattern jsonPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
            Matcher jsonMatcher = jsonPattern.matcher(content);
            
            while (jsonMatcher.find()) {
                Grievance grievance = parseGrievanceFromJson(jsonMatcher.group(1));
                if (grievance != null) {
                    grievances.add(grievance);
                }
            }
        } catch (Exception e) {
            UIHelper.showError("Failed to load grievances: " + e.getMessage());
        }
        
        return grievances;
    }
    
    private Student parseStudentFromJson(String jsonContent) {
        try {
            // Match the actual field names in students.json: "sr" and "passwordHash"
            String password = extractJsonField(jsonContent, "passwordHash");
            String name = extractJsonField(jsonContent, "name");
            String srCode = extractJsonField(jsonContent, "sr");
            String email = extractJsonField(jsonContent, "email");
            String validatedStr = extractJsonField(jsonContent, "validated");
            // Default to true for existing students if validated field doesn't exist
            boolean validated = (validatedStr == null || validatedStr.isEmpty()) ? true : Boolean.parseBoolean(validatedStr);
            
            Student student = new Student(srCode, name, email, password);
            student.setValidated(validated);
            return student;
        } catch (Exception e) {
            return null;
        }
    }
    
    private Grievance parseGrievanceFromJson(String jsonContent) {
        try {
            String grievanceIdStr = extractJsonField(jsonContent, "id");
            int grievanceId = Integer.parseInt(grievanceIdStr);
            String studentSr = extractJsonField(jsonContent, "studentSR");
            String title = extractJsonField(jsonContent, "title");
            String category = extractJsonField(jsonContent, "category");
            String description = extractJsonField(jsonContent, "description");
            String status = extractJsonField(jsonContent, "status");
            String feedback = extractJsonField(jsonContent, "feedback");
            
            return new Grievance(grievanceId, studentSr, title, category, description, status, feedback);
        } catch (Exception e) {
            return null;
        }
    }
    
    private String extractJsonField(String jsonContent, String fieldName) {
        Pattern pattern = Pattern.compile("\"" + fieldName + "\"\\s*:\\s*\"([^\"]*)\"|\"" + 
                                        fieldName + "\"\\s*:\\s*([^,}\\s]+)");
        Matcher matcher = pattern.matcher(jsonContent);
        
        if (matcher.find()) {
            String quotedValue = matcher.group(1);
            if (quotedValue != null) {
                return quotedValue;
            }
            String unquotedValue = matcher.group(2);
            return unquotedValue != null ? unquotedValue : "";
        }
        return "";
    }
    
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
