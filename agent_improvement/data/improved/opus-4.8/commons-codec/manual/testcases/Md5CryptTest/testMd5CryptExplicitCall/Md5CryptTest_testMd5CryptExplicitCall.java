package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Md5Crypt#md5Crypt(byte[])} and its salt-accepting overload
 * produce hash strings in the libc6 crypt() "$1$" format.
 */
// A short timeout guards against the occasional hang seen while testing.
@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCall {

    /** Plaintext password used as input to the hash function. */
    private static final String PASSWORD = "secret";

    /**
     * Expected shape of a "$1$" MD5 crypt hash:
     * <ul>
     *   <li>the literal "$1$" prefix,</li>
     *   <li>a salt of 0 to 8 characters from the crypt alphabet ([a-zA-Z0-9./]),</li>
     *   <li>a "$" separator followed by the (non-empty) hashed value.</li>
     * </ul>
     */
    private static final String MD5_CRYPT_FORMAT = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCall() {
        // A fresh byte array is needed per call because md5Crypt zeroes out the
        // key bytes it receives before returning.

        // With an auto-generated salt.
        final String hashWithGeneratedSalt = Md5Crypt.md5Crypt(PASSWORD.getBytes());
        assertTrue(hashWithGeneratedSalt.matches(MD5_CRYPT_FORMAT));

        // With an explicit null salt, which also triggers salt auto-generation.
        final String hashWithNullSalt = Md5Crypt.md5Crypt(PASSWORD.getBytes(), (String) null);
        assertTrue(hashWithNullSalt.matches(MD5_CRYPT_FORMAT));
    }
}
