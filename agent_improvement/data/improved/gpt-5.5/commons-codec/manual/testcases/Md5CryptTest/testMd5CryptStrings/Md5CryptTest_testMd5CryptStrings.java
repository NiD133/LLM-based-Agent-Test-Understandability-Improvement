package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptStrings {

    @Test
    void testMd5CryptStrings() {
        assertMd5Crypt("", "$1$foo", "$1$foo$9mS5ExwgIECGE5YKlD5o91");

        assertMd5Crypt("secret", "$1$1234", "$1$1234$ImZYBLmYC.rbBKg9ERxX70");
        assertMd5Crypt("secret", "$1$1234$567", "$1$1234$ImZYBLmYC.rbBKg9ERxX70");
        assertMd5Crypt("secret", "$1$1234$567$890", "$1$1234$ImZYBLmYC.rbBKg9ERxX70");

        assertMd5Crypt("secret", "$1$1234567890123456", "$1$12345678$hj0uLpdidjPhbMMZeno8X/");
        assertMd5Crypt("secret", "$1$123456789012345678", "$1$12345678$hj0uLpdidjPhbMMZeno8X/");
    }

    private static void assertMd5Crypt(final String password, final String salt, final String expectedHash) {
        assertEquals(expectedHash, Crypt.crypt(password, salt));
    }
}
