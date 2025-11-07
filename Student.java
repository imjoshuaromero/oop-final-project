// Student class, nag-extend ng User para ma-inherit yung properties niya
public class Student extends User {
    // private kaya hindi makikita sa ibang classes, encapsulation ito
    private String srCode;

    // constructor para gumawa ng bagong student
    public Student(String srCode, String name, String password) {
        super(srCode, password, name);  // tumatawag sa constructor ng User (parent class)
        this.srCode = srCode;  // tapos i-save yung SR code ng student
    }

    // getter para makuha yung SR code ng student
    public String getSrCode() {
        return srCode;
    }

    // override ng displayInfo galing sa User class
    @Override
    public void displayInfo() {
        System.out.println("=== Student Information ===");
        System.out.println("SR-Code: " + srCode);
        super.displayInfo();  // tumatawag sa displayInfo ng parent (User)
        System.out.println("========================");
    }
}