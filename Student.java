public class Student {
    private String srCode;
    private String name;
    private String password;


    public Student(String srCode, String name, String password) {
        this.srCode = srCode;
        this.name = name;
        this.password = password;
    }


    public String getSrCode() {
        return srCode;
    }


    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }
}