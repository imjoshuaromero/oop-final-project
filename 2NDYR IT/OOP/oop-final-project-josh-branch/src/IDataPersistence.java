import java.util.ArrayList;

/**
 * Interface for data persistence operations
 * Abstracts file I/O from business logic
 */
public interface IDataPersistence {
    /**
     * Load all application data
     * @return AppData containing students and grievances
     */
    AppData loadApplicationData();
    
    /**
     * Save students to persistent storage
     * @param students List of students to save
     */
    void saveStudents(ArrayList<Student> students);
    
    /**
     * Save grievances to persistent storage
     * @param grievances List of grievances to save
     */
    void saveGrievances(ArrayList<Grievance> grievances);
}
