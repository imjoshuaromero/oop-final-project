// eto yung parent class para sa lahat ng users sa system
public class User {
    // protected para ma-access ng mga child classes (Student at Admin)
    protected String username;
    protected String password;
    protected String name;

    // constructor para gumawa ng bagong user
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    // mga getters para makuha yung info ng user
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // common method na magagamit ng lahat ng users
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
    }
}
