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

    // allow changing password for users
    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // note (tagalog): gumagamit ang `setPassword` para palitan ang password ng user.
    // - tinatawag kapag gustong mag-update ang user ng bagong password.
    // - simple setter lang ito; kung gusto mong mag-hash ng password bago i-save,
    //   pwede natin idagdag ang hashing dito sa susunod na hakbang.

    // common method na magagamit ng lahat ng users
    public void displayInfo() {
        System.out.println("Username: " + username);
        System.out.println("Name: " + name);
    }
}
