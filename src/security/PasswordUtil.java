package security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    private static final int ITERATIONS = 120000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;

    // Password ko hash karta hai
    public static String hashPassword(String password) {

        try {

            byte[] salt = new byte[SALT_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            spec.clearPassword();

            return Base64.getEncoder().encodeToString(salt)
                    + ":"
                    + Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Password hashing failed.", e
            );
        }
    }

    // Password verify karta hai
    public static boolean verifyPassword(
            String password,
            String storedPassword) {

        try {

            if (storedPassword == null ||
                    !storedPassword.contains(":")) {

                return false;
            }

            String[] parts = storedPassword.split(":");

            if (parts.length != 2) {
                return false;
            }

            byte[] salt =
                    Base64.getDecoder().decode(parts[0]);

            byte[] storedHash =
                    Base64.getDecoder().decode(parts[1]);

            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256"
                    );

            byte[] newHash =
                    factory.generateSecret(spec).getEncoded();

            spec.clearPassword();

            return MessageDigest.isEqual(
                    storedHash,
                    newHash
            );

        } catch (Exception e) {

            return false;
        }
    }

    // Check karta hai ki password already hashed hai ya nahi
    public static boolean isHashed(String password) {

        if (password == null) {
            return false;
        }

        return password.contains(":");
    }

    public static void main(String[] args) {

        String password = "riya123";

        String hash =
                hashPassword(password);

        System.out.println("Password Hash:");
        System.out.println(hash);
    }
}
