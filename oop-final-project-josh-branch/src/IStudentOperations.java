import java.util.ArrayList;

/**
 * Interface defining student-related operations
 * This promotes loose coupling and allows different implementations
 */
public interface IStudentOperations {
    /**
     * Register a new student in the system
     * @param students List of existing students
     */
    void registerStudent(ArrayList<Student> students);
    
    /**
     * Validate all registered students against campus registry
     * @param students List of students to validate
     */
    void validateRegisteredStudents(ArrayList<Student> students);
    
    /**
     * Allow student to file a new concern
     * @param student The student filing the concern
     * @param grievances List of all grievances
     */
    void fileConcern(Student student, ArrayList<Grievance> grievances);
    
    /**
     * Display all concerns filed by a specific student
     * @param student The student whose concerns to display
     * @param grievances List of all grievances
     */
    void viewStudentConcerns(Student student, ArrayList<Grievance> grievances);
}
