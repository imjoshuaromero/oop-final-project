public class Main {
    public static void main(String[] args) {
        AppData appData = DataManager.loadApplicationData();
        MenuHandler.runMainMenu(appData.getStudents(), appData.getGrievances());
    }
}

//pantayin ang UI sa console
//ittable ang view concerns
//iprivate ang ibang fields sa user