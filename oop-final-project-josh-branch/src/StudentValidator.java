import java.util.Set;
import java.util.HashSet;
// no arrays import needed any more
import java.util.regex.Pattern;

// helper class para mag-validate ng sr-codes mula sa campus registry at mag-validate ng email
// simple helper na ginagamit ng main program para tiyakin na ang sr-code ay kabilang sa listahan
// at para i-validate ang email format. naka-load mula sa `students.txt` para madaling i-maintain.
public class StudentValidator {
    // registry of sr-codes. loaded from students.txt at runtime so the list is maintainable
    private static final Set<String> VALID_SR_CODES = new HashSet<>();

    static {
    // sa unang pag-load ng class, susubukan nating basahin ang `students.txt`.
    // kung may laman ang file, ang mga sr-code doon ay ilalagay sa registry.
        loadFromFile();
    }

    // load sr codes from students.txt (format: sr|name). this is safe to call multiple times.
    // basahin ang mga sr-code mula sa `students.txt`.
    // format ng bawat linya: sr|name  o  sr|name|email|password
    // pwede itong tawagin muli para i-refresh ang registry habang tumatakbo ang app.
    public static void loadFromFile() {
        VALID_SR_CODES.clear();
        java.io.File f = new java.io.File("students.json");
        if (!f.exists()) {
            // no file found — leave registry empty
            return;
        }

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            // find objects like {"sr":"24-12345", "name":"...", "email":"...", "passwordHash":"..."}
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\{\\s*\"sr\"\\s*:\\s*\"(.*?)\"", java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher m = p.matcher(content);
            while (m.find()) {
                String sr = m.group(1).trim();
                if (sr.matches("\\d{2}-\\d{5}")) VALID_SR_CODES.add(sr);
            }
        } catch (Exception e) {
            System.out.println("StudentValidator: Error loading students.json: " + e.getMessage());
        }
    }

    // convenience to reload at runtime
    public static void reloadRegistry() {
        loadFromFile();
    }

    // simple email regex (reasonable for validation in this context)
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    // check if sr-code exists in the sampled campus registry
    public static boolean isCampusStudent(String srCode) {
        if (srCode == null) return false;
        return VALID_SR_CODES.contains(srCode.trim());
    }

    // validate email format
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    // expose registry size for reporting
    public static int registrySize() {
        return VALID_SR_CODES.size();
    }
}
