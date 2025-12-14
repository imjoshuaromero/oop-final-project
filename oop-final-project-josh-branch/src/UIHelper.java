// UIHelper: Handles common UI messages and displays with centered bordered boxes
public class UIHelper {
    
    // Helper method to display a centered message in a bordered box
    private static void showMessageBox(String message) {
        int consoleWidth = Utility.getConsoleWidth();
        int boxWidth = 50; // Fixed width for consistent alignment
        int contentWidth = boxWidth - 2; // Width between the │ characters (48 chars)
        int leftPadding = (consoleWidth - boxWidth) / 2;
        String centerPad = " ".repeat(Math.max(0, leftPadding));
        
        // Split message by newlines
        String[] lines = message.split("\\n");
        
        // Top border
        System.out.println(centerPad + "┌" + "─".repeat(contentWidth) + "┐");
        
        // Each line with padding
        for (String line : lines) {
            String messageLine;
            if (line.length() <= contentWidth) {
                int totalPad = contentWidth - line.length();
                int leftSpace = totalPad / 2;
                int rightSpace = totalPad - leftSpace;
                messageLine = " ".repeat(leftSpace) + line + " ".repeat(rightSpace);
            } else {
                // Line too long, truncate it
                messageLine = line.substring(0, contentWidth);
            }
            System.out.println(centerPad + "│" + messageLine + "│");
        }
        
        // Bottom border
        System.out.println(centerPad + "└" + "─".repeat(contentWidth) + "┘");
    }
    
    public static void showInvalidChoice(int min, int max) {
        Utility.printCentered("");
        showMessageBox("Invalid Choice!");
        Utility.printCentered("Please select a number between " + min + " and " + max + ".");
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showError(String message) {
        Utility.printCentered("");
        showMessageBox(message);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showSuccess(String message) {
        Utility.printCentered("");
        showMessageBox(message);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void showLoginSuccess(String name) {
        Utility.clearScreen();
        Utility.printCentered("");
        showMessageBox("Login Successful!");
        showMessageBox("Welcome, " + name);
        Utility.printCentered("");
        Utility.waitForEnter("Press Enter to continue...");
    }
    
    public static void exitProgram() {
        Utility.overlaySpinner("Exiting Program", 1000);
        Utility.clearScreen();
        Utility.printCentered("");
        showMessageBox("Thank You For Using The System!");
        Utility.printCentered("");
    }
}
