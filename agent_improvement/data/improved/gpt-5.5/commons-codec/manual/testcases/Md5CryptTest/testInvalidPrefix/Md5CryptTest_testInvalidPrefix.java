package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testInvalidPrefix {

    private static final String LONG_SALT_WITH_SUFFIX =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";
    private static final String LONG_SALT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String UNBOUNDED_PREFIX_PATTERN = "(.*a){10000}";
    private static final String BOUNDED_PREFIX_PATTERN = "$(.*a){10000}$";

    @Test
    void testInvalidPrefix() {
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes(), LONG_SALT_WITH_SUFFIX, UNBOUNDED_PREFIX_PATTERN));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes(), LONG_SALT_WITH_SUFFIX, BOUNDED_PREFIX_PATTERN));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes(), LONG_SALT, BOUNDED_PREFIX_PATTERN));
    }

    private static byte[] keyBytes() {
        return new byte[] { 1, 2, 3, 4, 5 };
    }
}
