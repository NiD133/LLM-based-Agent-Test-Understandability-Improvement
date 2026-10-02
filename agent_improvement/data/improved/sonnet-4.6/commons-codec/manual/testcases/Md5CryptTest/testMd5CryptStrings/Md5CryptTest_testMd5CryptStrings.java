package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptStrings {

    /**
     * MD5 crypt produces a valid hash even when the password is the empty string.
     */
    @Test
    void testMd5CryptWithEmptyPassword() {
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));
    }

    /**
     * The salt is extracted up to (but not including) the first '$' after the prefix.
     * Any trailing '$'-separated segments are silently ignored, so all three inputs
     * produce the same hash as the bare four-character salt "$1$1234".
     */
    @ParameterizedTest(name = "salt input \"{1}\" is truncated at ''$'' to yield the same hash")
    @CsvSource({
        "$1$1234$ImZYBLmYC.rbBKg9ERxX70, $1$1234",
        "$1$1234$ImZYBLmYC.rbBKg9ERxX70, $1$1234$567",
        "$1$1234$ImZYBLmYC.rbBKg9ERxX70, $1$1234$567$890"
    })
    void testMd5CryptSaltTruncatedAtDollarSign(String expectedHash, String saltInput) {
        assertEquals(expectedHash, Crypt.crypt("secret", saltInput));
    }

    /**
     * The salt portion (after the "$1$" prefix) is capped at 8 characters.
     * Anything beyond position 8 is silently discarded, so both long-salt inputs
     * produce the same hash as the eight-character salt "12345678".
     */
    @ParameterizedTest(name = "salt input \"{1}\" is truncated at 8 chars to yield the same hash")
    @CsvSource({
        "$1$12345678$hj0uLpdidjPhbMMZeno8X/, $1$1234567890123456",
        "$1$12345678$hj0uLpdidjPhbMMZeno8X/, $1$123456789012345678"
    })
    void testMd5CryptSaltTruncatedAtMaxLength(String expectedHash, String saltInput) {
        assertEquals(expectedHash, Crypt.crypt("secret", saltInput));
    }
}
