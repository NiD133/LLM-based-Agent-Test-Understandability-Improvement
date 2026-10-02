package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testInvalidPrefix {

    // Password bytes used across all invalid-prefix cases
    private static final byte[] PASSWORD_BYTES = new byte[] { 1, 2, 3, 4, 5 };

    // A long salt composed of 'a' characters, with an invalid trailing '!'
    private static final String SALT_WITH_INVALID_CHAR =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";

    // A long salt composed only of 'a' characters (no invalid trailing character)
    private static final String SALT_ALL_ALPHA =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    // A prefix that lacks the required leading '$' delimiter — structurally invalid
    private static final String PREFIX_MISSING_DOLLAR_SIGN = "(.*a){10000}";

    // A prefix that looks structurally valid (starts and ends with '$') but contains a
    // ReDoS-prone sub-pattern; must still be rejected by the implementation
    private static final String PREFIX_REDOS_PATTERN = "$(.*a){10000}$";

    @Test
    void testInvalidPrefix() {
        // Case 1: prefix does not start with '$' — the implementation must reject it immediately
        // as an invalid prefix before any pattern matching can occur
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(PASSWORD_BYTES, SALT_WITH_INVALID_CHAR, PREFIX_MISSING_DOLLAR_SIGN));

        // Case 2: prefix is structurally plausible (has '$' delimiters) but combined with a salt
        // ending in '!' (outside the allowed [./a-zA-Z0-9] character set) — must throw before
        // any catastrophic backtracking can occur
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(PASSWORD_BYTES, SALT_WITH_INVALID_CHAR, PREFIX_REDOS_PATTERN));

        // Case 3: same ReDoS-prone prefix with a salt containing only 'a's — the salt does not
        // match the prefix pattern, so an IllegalArgumentException must be thrown within the
        // @Timeout(3) limit rather than hanging on catastrophic backtracking
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(PASSWORD_BYTES, SALT_ALL_ALPHA, PREFIX_REDOS_PATTERN));
    }
}
