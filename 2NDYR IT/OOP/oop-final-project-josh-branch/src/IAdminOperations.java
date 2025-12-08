import java.util.ArrayList;

/**
 * Interface defining admin-related operations
 * Separates admin responsibilities from implementation
 */
public interface IAdminOperations {
    /**
     * View all concerns submitted by students
     * @param grievances List of all grievances
     */
    void viewAllConcerns(ArrayList<Grievance> grievances);
    
    /**
     * Update status and feedback for a specific concern
     * @param grievances List of all grievances
     */
    void updateConcern(ArrayList<Grievance> grievances);
    
    /**
     * Delete a concern from the system
     * @param grievances List of all grievances
     */
    void deleteConcern(ArrayList<Grievance> grievances);
}
