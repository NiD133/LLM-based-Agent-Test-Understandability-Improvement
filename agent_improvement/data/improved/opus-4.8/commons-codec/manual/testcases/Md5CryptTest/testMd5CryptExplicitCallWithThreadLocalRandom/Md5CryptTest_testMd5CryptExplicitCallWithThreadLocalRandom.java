package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Md5Crypt#md5Crypt(byte[], java.util.Random)} produces a
 * well-formed libc6 "$1$" MD5 crypt hash when the caller lets the method
 * generate the salt itself (either from a supplied {@link ThreadLocalRandom}
 * or by passing a {@code null} salt string, which makes the method fall back to
 * its own random salt generation).
 */
// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCallWithThreadLocalRandom {

    /**
     * Plaintext password to hash. A fresh byte array is produced per call
     * because {@link Md5Crypt#md5Crypt} zeroes out the key array it receives.
     */
    private static final String PASSWORD = "secret";

    /**
     * Expected shape of a libc6 "$1$" MD5 crypt result:
     * the "$1$" prefix, then up to 8 salt characters drawn from the base64
     * alphabet [a-zA-Z0-9./], then "$", then the non-empty hashed password.
     */
    private static final String MD5_CRYPT_FORMAT = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        // Salt generated from a caller-supplied Random source.
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        final String hashFromRandom = Md5Crypt.md5Crypt(PASSWORD.getBytes(), threadLocalRandom);
        assertTrue(hashFromRandom.matches(MD5_CRYPT_FORMAT),
                "Hash from supplied Random should match the $1$ MD5 crypt format: " + hashFromRandom);

        // Null salt: the method must generate a salt internally and still succeed.
        final String hashFromNullSalt = Md5Crypt.md5Crypt(PASSWORD.getBytes(), (String) null);
        assertTrue(hashFromNullSalt.matches(MD5_CRYPT_FORMAT),
                "Hash from null salt should match the $1$ MD5 crypt format: " + hashFromNullSalt);
    }
}
