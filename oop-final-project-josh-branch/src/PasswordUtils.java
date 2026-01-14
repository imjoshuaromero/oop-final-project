import java.security.MessageDigest;

// passwordutils: helper para i-hash ang passwords gamit ang sha-256
// ginagawa ito para hindi natin i-store ang plain text passwords sa file
public class PasswordUtils {
    // simple sha-256 hash, nagbabalik ng hex string
    public static String hash(String input) {
        if (input == null) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
