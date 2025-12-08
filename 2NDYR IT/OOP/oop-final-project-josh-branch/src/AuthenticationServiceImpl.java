import java.util.ArrayList;

/**
 * Implementation of authentication service
 * Handles student/admin authentication and password operations
 */
public class AuthenticationServiceImpl implements IAuthenticationService {
    private static final String DEFAULT_ADMIN_USERNAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "admin";
    
    @Override
    public Student authenticateStudent(ArrayList<Student> students, String srCode, String password) {
        String hashedPassword = PasswordUtils.hash(password);
        
        for (Student student : students) {
            if (student.getSrCode().equals(srCode) && 
                student.getPassword() != null && 
                student.getPassword().equals(hashedPassword)) {
                
                if (!student.isValidated()) {
                    return null; // Student not yet validated by admin
                }
                return student;
            }
        }
        return null;
    }
    
    @Override
    public boolean authenticateAdmin(String userName, String password) {
        return userName.equals(DEFAULT_ADMIN_USERNAME) && 
               password.equals(DEFAULT_ADMIN_PASSWORD);
    }
    
    @Override
    public boolean changePassword(Student student, String currentPassword, String newPassword, String confirmPassword) {
        // Verify current password
        if (!student.getPassword().equals(PasswordUtils.hash(currentPassword))) {
            UIHelper.showError("Current password is incorrect!");
            return false;
        }
        
        // Check if passwords match
        if (!newPassword.equals(confirmPassword)) {
            UIHelper.showError("Passwords do not match!");
            return false;
        }
        
        // Check if password is not empty
        if (newPassword.trim().isEmpty()) {
            UIHelper.showError("Password cannot be empty!");
            return false;
        }
        
        // Update password
        student.setPassword(PasswordUtils.hash(newPassword));
        return true;
    }
    
    @Override
    public void resetStudentPassword(ArrayList<Student> students, String srCode) {
        // Trim input to remove any extra whitespace
        srCode = srCode.trim();
        
        Student targetStudent = null;
        
        for (Student student : students) {
            if (student.getSrCode().trim().equals(srCode)) {
                targetStudent = student;
                break;
            }
        }
        
        if (targetStudent == null) {
            UIHelper.showError("Student with SR-Code " + srCode + " not found!\nTotal students in system: " + students.size());
            return;
        }
        
        // Reset password to SR code
        targetStudent.setPassword(PasswordUtils.hash(targetStudent.getSrCode()));
        DataManager.saveStudents(students);
        
        UIHelper.showSuccess("Password reset successfully for " + targetStudent.getName() + "!\nNew password: " + srCode);
    }
}
