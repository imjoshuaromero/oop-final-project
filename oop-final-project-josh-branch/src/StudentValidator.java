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

    // load sr codes from sr_registry.json (campus registry). this is safe to call multiple times.
    // basahin ang mga sr-code mula sa `sr_registry.json` (campus student list).
    // format: JSON array with objects containing "sr" field (e.g., [{"sr": "24-31688"}])
    // pwede itong tawagin muli para i-refresh ang registry habang tumatakbo ang app.
    public static void loadFromFile() {
        VALID_SR_CODES.clear();
        java.io.File f = new java.io.File("sr_registry.json");
        if (!f.exists()) {
            // no file found — leave registry empty
            System.out.println("StudentValidator: sr_registry.json not found!");
            return;
        }

        try {
            String content = new String(java.nio.file.Files.readAllBytes(f.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            // Parse JSON array manually
            content = content.trim();
            if (content.startsWith("[") && content.endsWith("]")) {
                content = content.substring(1, content.length() - 1);
                String[] objects = content.split("},");
                for (String obj : objects) {
                    // Extract SR code from JSON object: {"sr": "24-31688"}
                    int srStart = obj.indexOf("\"sr\"");
                    if (srStart != -1) {
                        int colonPos = obj.indexOf(":", srStart);
                        int quoteStart = obj.indexOf("\"", colonPos);
                        int quoteEnd = obj.indexOf("\"", quoteStart + 1);
                        if (quoteStart != -1 && quoteEnd != -1) {
                            String sr = obj.substring(quoteStart + 1, quoteEnd).trim();
                            if (sr.matches("\\d{2}-\\d{5}")) {
                                VALID_SR_CODES.add(sr);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("StudentValidator: Error loading sr_registry.json: " + e.getMessage());
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
