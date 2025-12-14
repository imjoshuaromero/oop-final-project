import java.util.ArrayList;

/**
 * Interface for authentication operations
 * Abstracts authentication logic from implementation details
 */
public interface IAuthenticationService {
    /**
     * Authenticate a student with credentials
     * @param students List of registered students
     * @param srCode Student SR code
     * @param password Student password
     * @return Student object if authenticated, null otherwise
     */
    Student authenticateStudent(ArrayList<Student> students, String srCode, String password);
    
    /**
     * Authenticate an admin with credentials
     * @param userName Admin username
     * @param password Admin password
     * @return true if authenticated, false otherwise
     */
    boolean authenticateAdmin(String userName, String password);
    
    /**
     * Change student password
     * @param student Student whose password to change
     * @param currentPassword Current password for verification
     * @param newPassword New password
     * @param confirmPassword Password confirmation
     * @return true if successful, false otherwise
     */
    boolean changePassword(Student student, String currentPassword, String newPassword, String confirmPassword);
    
    /**
     * Reset student password to default (SR code)
     * @param students List of all students
     * @param srCode SR code of student whose password to reset
     */
    void resetStudentPassword(ArrayList<Student> students, String srCode);
}
