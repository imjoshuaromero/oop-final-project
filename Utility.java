import java.util.Scanner;

// utility: shared helper methods para iwas-ulit at para mas malinis ang main code.
// lahat ng methods ay may tagalog comments para madaling maintindihan.
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    // nagpapakita ng simple header/intro (may delay para effect)
    public static void header() throws InterruptedException {
        Thread.sleep(500);
        System.out.println("\t\t\t****************************");
        Thread.sleep(300);
        System.out.println("\t\t\t********** Welcome ***********");
        Thread.sleep(300);
        System.out.println("\t\t\t****************************");
        Thread.sleep(300);
    }

    // linawin ang console screen (gumagawa ng external command depende sa os)
    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    // simpleng loading animation (overlay): ipinapakita ang loading sa iisang linya
    // at nililinis ang screen para hindi dumami ang output kapag maraming actions
    public static void loadingScreen() {
        overlaySpinner("Loading...", 1200);
    }

    // print a simple boxed title/header to keep menus consistent
    public static void printTitle(String title) {
        // enhanced double-line box design with better spacing
        int width = Math.min(60, getConsoleWidth() - 10);
        int titleLen = title.length();
        int innerWidth = Math.max(titleLen + 4, width);
        
        String topBorder = "═".repeat(innerWidth);
        String bottomBorder = "═".repeat(innerWidth);
        int padding = (innerWidth - titleLen - 2) / 2;
        String leftPad = " ".repeat(padding);
        String rightPad = " ".repeat(innerWidth - titleLen - padding - 2);
        
        printCentered("╔" + topBorder + "╗");
        printCentered("║" + leftPad + title.toUpperCase() + rightPad + "║");
        printCentered("╚" + bottomBorder + "╝");
    }

    // banner-style title with decorative elements (for main welcome screen)
    public static void printBanner(String title) {
        int width = Math.min(60, getConsoleWidth() - 10);
        String stars = "*".repeat(width);
        String titleLine = String.format("%-" + width + "s", String.format("%" + ((width + title.length()) / 2) + "s", title.toUpperCase()));
        
        printCentered("");
        printCentered(stars);
        printCentered("*****" + " ".repeat(width - 10) + "*****");
        printCentered(titleLine);
        printCentered("*****" + " ".repeat(width - 10) + "*****");
        printCentered(stars);
        printCentered("");
    }

    // gradient-style title with ASCII art effect
    public static void printFancyTitle(String title) {
        String line1 = "▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓";
        String line2 = "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░";
        
        printCentered("");
        printCentered(line1);
        printCentered("▓▓▓▓▓         " + title.toUpperCase() + "         ▓▓▓▓▓");
        printCentered(line1);
        printCentered(line2);
        printCentered("");
    }

    // simple and clean title with underline
    public static void printSimpleTitle(String title) {
        String underline = "─".repeat(title.length() + 4);
        printCentered("");
        printCentered("  " + title.toUpperCase() + "  ");
        printCentered(underline);
        printCentered("");
    }

    // wave-style decorative title
    public static void printWaveTitle(String title) {
        String wave = "~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~·~";
        printCentered("");
        printCentered(wave);
        printCentered("》》》  " + title.toUpperCase() + "  《《《");
        printCentered(wave);
        printCentered("");
    }

    // wait for user to press enter (useful after displaying a report)
    public static void waitForEnter(String prompt) {
        // show a nicely capitalized prompt centered on the screen
        printCenteredInline(prompt);
        scanner.nextLine();
    }

    // print text centered in a default console width (80 columns)
    public static void printCentered(String text) {
        int width = getConsoleWidth();
        if (text == null) text = "";
        if (text.length() >= width) {
            System.out.println(text);
            return;
        }
        int pad = Math.max(0, (width - text.length()) / 2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pad; i++) sb.append(' ');
        sb.append(text);
        System.out.println(sb.toString());
    }

    // print centered text WITHOUT newline (so the input cursor remains after the text)
    public static void printCenteredInline(String text) {
        int width = getConsoleWidth();
        if (text == null) text = "";
        if (text.length() >= width) {
            // ensure a trailing space for input separation
            if (text.length() == 0 || Character.isWhitespace(text.charAt(text.length()-1))) System.out.print(text);
            else System.out.print(text + " ");
            return;
        }
        int pad = Math.max(0, (width - text.length()) / 2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pad; i++) sb.append(' ');
        sb.append(text);
        // add a trailing space so the user's input isn't glued to the prompt
        if (sb.length() == 0 || Character.isWhitespace(sb.charAt(sb.length()-1))) System.out.print(sb.toString());
        else System.out.print(sb.toString() + " ");
    }

    // overlay spinner: clears the screen, prints the message centered and updates the
    // spinner character in-place so the console doesn't grow vertically.
    public static void overlaySpinner(String message, int durationMs) {
        if (message == null) message = "";
        clearScreen();
        int width = getConsoleWidth();
        int pad = Math.max(0, (width - message.length()) / 2);
        String padStr = new String(new char[pad]).replace('\0', ' ');
        char[] spin = {'|', '/', '-', '\\'};
        long end = System.currentTimeMillis() + durationMs;

        try {
            int i = 0;
            while (System.currentTimeMillis() < end) {
                String out = padStr + message + " " + spin[i % spin.length] + " ";
                System.out.print("\r" + out);
                System.out.flush();
                Thread.sleep(120);
                i++;
            }
        } catch (InterruptedException e) {
            // ignore
        }
        // leave final message
        System.out.print("\r" + padStr + message + "   ");
        System.out.println();
        System.out.flush();
    }

    // try to detect console width; fall back to 80
    private static int cachedWidth = -1;
    public static int getConsoleWidth() {
        if (cachedWidth > 0) return cachedWidth;
        int fallback = 80;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // windows: run 'mode con' and look specifically for a 'Columns:' line
                Process p = new ProcessBuilder("cmd", "/c", "mode con").redirectErrorStream(true).start();
                java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
                String line;
                java.util.regex.Pattern cols = java.util.regex.Pattern.compile("(?i)columns\\s*:\\s*(\\d+)");
                while ((line = r.readLine()) != null) {
                    java.util.regex.Matcher m = cols.matcher(line);
                    if (m.find()) {
                        try { cachedWidth = Integer.parseInt(m.group(1)); break; } catch (Exception ex) { }
                    }
                }
                p.destroy();
            } else {
                // unix-like: prefer 'tput cols' which prints a single number
                Process p = new ProcessBuilder("sh", "-c", "tput cols").redirectErrorStream(true).start();
                java.io.BufferedReader r = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
                String line = r.readLine();
                if (line != null) {
                    line = line.trim();
                    try { cachedWidth = Integer.parseInt(line); }
                    catch (Exception ex) { /* ignore */ }
                }
                p.destroy();
            }
        } catch (Exception e) {
            // ignore and use fallback
        }
        if (cachedWidth <= 0) cachedWidth = fallback;
        return cachedWidth;
    }

    // print a vertical menu with each option centered
    public static void printMenu(String[] options) {
        boolean lastWasBlank = false;
        for (String opt : options) {
            if (opt == null) opt = "";
            if (opt.trim().isEmpty()) {
                if (!lastWasBlank) { System.out.println(); lastWasBlank = true; }
            } else {
                printCentered(opt);
                lastWasBlank = false;
            }
        }
    }

    // prompt centered and read a string
    public static String promptCenteredString(String prompt) {
        // print prompt centered on the same line and read the user's input
        printCenteredInline(prompt);
        return scanner.nextLine();
    }

    // prompt centered and read an int (returns 0 on invalid input)
    public static int promptCenteredInt(String prompt) {
        // print prompt centered on the same line and read the user's input as a line
        printCenteredInline(prompt);
        String line = scanner.nextLine();
        try {
            return Integer.parseInt(line.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    // backward-compatible spinner wrapper that uses overlaySpinner
    public static void spinner(String message, int durationMs) {
        if (message == null) message = "";
        // capitalize first letter of the message if not already
        if (!message.isEmpty()) message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
        overlaySpinner(message, durationMs);
    }

    // overlay loading bar: clears the screen and updates a single progress bar in-place
    public static void loadingBar(String message, int totalMs, int steps) {
        clearScreen();
        if (message == null) message = "";
        int width = getConsoleWidth();
        int pad = Math.max(0, (width - (message.length() + steps + 10)) / 2);
        String padStr = new String(new char[pad]).replace('\0', ' ');
        try {
            System.out.print(padStr + message + " [");
            System.out.flush();
            for (int i = 0; i < steps; i++) {
                Thread.sleep(Math.max(1, totalMs / steps));
                System.out.print("=");
                System.out.flush();
            }
            System.out.println("] Done");
        } catch (InterruptedException e) {
            System.out.println(" - Interrupted");
        }
    }

    // scanner helper para sa string input; ipapakita ang prompt at babasahin ang buong linya
    public static String stringScanner(String input) {
        System.out.print(input);
        return scanner.nextLine();
    }

    // scanner helper para sa integer input; ipapakita ang prompt at babasahin ang integer
    // nag-poproseso din ng nextLine() para malinis ang buffer
    public static int intScanner(String input) {
        System.out.print(input);
        int value = 0;
        try {
            value = scanner.nextInt();
        } catch (Exception e) {
            // kung invalid ang input, i-clear ang scanner at ibalik 0
            scanner.nextLine();
            return 0;
        }
        scanner.nextLine();
        return value;
    }

    // isara ang scanner kapag mag-eexit na ang program
    public static void closeScanner() {
        try {
            scanner.close();
        } catch (Exception e) {
            // ignore
        }
    }
}
