import java.util.ArrayList;

// AuthService: Handles authentication and user management
public class AuthService {
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    private static final String SR_CODE_PATTERN = "\\d{2}-\\d{5}";
    
    // ========================= STUDENT AUTHENTICATION =========================
    
    public static Student authenticateStudent(ArrayList<Student> students, String srCode, String password) {
        String hashedPassword = PasswordUtils.hash(password);
        for (Student student : students) {
            if (student.getSrCode().equals(srCode) && 
                student.getPassword() != null && 
                student.getPassword().equals(hashedPassword)) {
                return student;
            }
        }
        return null;
    }
    
    public static Student findStudentBySrCode(ArrayList<Student> students, String srCode) {
        for (Student st : students) {
            if (st.getSrCode().equals(srCode)) return st;
        }
        return null;
    }
    
    public static boolean isDuplicateStudent(ArrayList<Student> students, String srCode) {
        return findStudentBySrCode(students, srCode) != null;
    }
    
    public static boolean isValidSrCodeFormat(String srCode) {
        return srCode.matches(SR_CODE_PATTERN);
    }
    
    // ========================= ADMIN AUTHENTICATION =========================
    
    public static boolean authenticateAdmin(String username, String password) {
        return username.equals(DEFAULT_ADMIN_USERNAME) && password.equals(DEFAULT_ADMIN_PASSWORD);
    }
    
    // ========================= PASSWORD OPERATIONS =========================
    
    public static boolean changePassword(Student student, String currentPassword, String newPassword, String confirmPassword) {
        // Verify current password
        if (!student.getPassword().equals(PasswordUtils.hash(currentPassword))) {
            UIHelper.showError("Current Password Incorrect.");
            return false;
        }
        
        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            UIHelper.showError("Passwords Do Not Match.");
            return false;
        }
        
        // Check if password is not empty
        if (newPassword.isEmpty()) {
            UIHelper.showError("Password Cannot Be Empty.");
            return false;
        }
        
        // Update password
        student.setPassword(PasswordUtils.hash(newPassword));
        return true;
    }
    
    public static void resetStudentPassword(ArrayList<Student> students, String srCode) {
        Student found = findStudentBySrCode(students, srCode);
        
        if (found == null) {
            UIHelper.showError("Student Not Found!\nPlease check the SR-Code and try again.");
        } else {
            found.setPassword(PasswordUtils.hash(found.getSrCode()));
            DataManager.saveStudents(students);
            UIHelper.showSuccess("Password Reset Successfully!\nPassword has been reset to SR-Code for: " + found.getSrCode());
        }
    }
}
