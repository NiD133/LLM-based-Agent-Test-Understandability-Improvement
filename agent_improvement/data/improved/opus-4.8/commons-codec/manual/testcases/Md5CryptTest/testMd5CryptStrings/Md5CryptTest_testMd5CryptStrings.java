package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies how the libc crypt() "$1$" MD5 algorithm parses the salt portion of
 * its input. The interesting behaviours are:
 * <ul>
 *   <li>an empty password still produces a valid hash,</li>
 *   <li>any text after a second '$' in the salt is ignored, and</li>
 *   <li>the salt is truncated to a maximum of 8 characters.</li>
 * </ul>
 */
// Try to avoid occasional hang when testing.
@Timeout(3)
public class Md5CryptTest_testMd5CryptStrings {

    @Test
    void testMd5CryptStrings() {
        // An empty password hashes successfully against the salt "foo".
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));

        // The salt "1234" produces a stable hash, and any characters after the
        // next '$' are treated as garbage and ignored, so all three of these
        // inputs yield the same result.
        final String saltedWith1234 = "$1$1234$ImZYBLmYC.rbBKg9ERxX70";
        assertEquals(saltedWith1234, Crypt.crypt("secret", "$1$1234"));
        assertEquals(saltedWith1234, Crypt.crypt("secret", "$1$1234$567"));
        assertEquals(saltedWith1234, Crypt.crypt("secret", "$1$1234$567$890"));

        // The salt is capped at 8 characters, so both a 16- and an 18-character
        // salt collapse to "12345678" and produce the same hash.
        final String saltedWith12345678 = "$1$12345678$hj0uLpdidjPhbMMZeno8X/";
        assertEquals(saltedWith12345678, Crypt.crypt("secret", "$1$1234567890123456"));
        assertEquals(saltedWith12345678, Crypt.crypt("secret", "$1$123456789012345678"));
    }
}
