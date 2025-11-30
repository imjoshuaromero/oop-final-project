// UIHelper: Handles common UI messages and displays
public class UIHelper {
    
    public static void showInvalidChoice(int min, int max) {
        Utility.printCentered("");
        Utility.printCentered("Invalid Choice!");
        Utility.printCentered("Please select a number between " + min + " and " + max + ".");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showError(String message) {
        Utility.printCentered("");
        Utility.printCentered("" + message);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showSuccess(String message) {
        Utility.printCentered("");
        Utility.printCentered("" + message);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showLoginSuccess(String name) {
        Utility.clearScreen();
        Utility.printCentered("");
        Utility.printCentered("✓ Login Successful!");
        Utility.printCentered("Welcome, " + name);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void exitProgram() {
        Utility.overlaySpinner("Exiting Program", 1000);
        Utility.clearScreen();
        Utility.printCentered("");
        Utility.printCentered("Thank You For Using The System!");
        Utility.printCentered("");
    }
}
