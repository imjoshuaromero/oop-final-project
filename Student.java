// student class, nag-extend ng user para ma-inherit yung properties niya
public class Student extends User {
    // private kaya hindi makikita sa ibang classes, encapsulation ito
    private String srCode;
    private String email;  // email ng student

    // constructor para gumawa ng bagong student
    public Student(String srCode, String name, String email, String password) {
        super(srCode, password, name);  // tumatawag sa constructor ng user (parent class)
        this.srCode = srCode;  // tapos i-save yung sr code ng student
        this.email = email;  // i-save din yung email
    }

    // getter para makuha yung sr code ng student
    public String getSrCode() {
        return srCode;
    }

    // getter para makuha yung email ng student
    public String getEmail() {
        return email;
    }

    // override ng displayInfo galing sa user class
    @Override
    public void displayInfo() {
        System.out.println("=== Student Information ===");
        System.out.println("SR-Code: " + srCode);
        super.displayInfo();  // tumatawag sa displayInfo ng parent (User)
        System.out.println("Email: " + email);  // i-display din yung email
        System.out.println("========================");
    }
}