import java.util.ArrayList;

// AppData: Container for application data (students and grievances)
public class AppData {
    private ArrayList<Student> students;
    private ArrayList<Grievance> grievances;
    
    public AppData(ArrayList<Student> students, ArrayList<Grievance> grievances) {
        this.students = students;
        this.grievances = grievances;
    }
    
    public ArrayList<Student> getStudents() {
        return students;
    }
    
    public ArrayList<Grievance> getGrievances() {
        return grievances;
    }
}
