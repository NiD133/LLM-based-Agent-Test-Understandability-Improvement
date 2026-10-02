package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that {@link Md5Crypt#md5Crypt(byte[], String, String)} rejects malformed
 * {@code prefix} / {@code salt} arguments by throwing {@link IllegalArgumentException}.
 *
 * <p>The {@link Timeout} guards against the catastrophic-backtracking regular expressions
 * used below (e.g. {@code (.*a){10000}}); the input is rejected before any expensive
 * matching can hang the test.</p>
 */
@Timeout(3)
public class Md5CryptTest_testInvalidPrefix {

    /** Arbitrary plaintext key; its concrete value is irrelevant because validation fails first. */
    private static final byte[] KEY_BYTES = { 1, 2, 3, 4, 5 };

    /** A long run of 'a' characters terminated by '!', which is not a legal salt character. */
    private static final String SALT_WITH_INVALID_CHAR =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";

    /** The same long run of 'a' characters, without the trailing '!'. */
    private static final String SALT_ALL_LETTERS =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    /** A prefix that neither starts nor ends with '$', so it is rejected as a prefix. */
    private static final String PREFIX_WITHOUT_DOLLARS = "(.*a){10000}";

    /** A prefix wrapped in '$' delimiters: it passes the prefix check but never matches the salt. */
    private static final String PREFIX_WITH_DOLLARS = "$(.*a){10000}$";

    @Test
    void prefixWithoutDollarDelimitersIsRejected() {
        // The prefix's first and last characters are neither '$', so it fails the prefix shape check.
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(KEY_BYTES, SALT_WITH_INVALID_CHAR, PREFIX_WITHOUT_DOLLARS));
    }

    @Test
    void saltContainingInvalidCharacterIsRejected() {
        // Prefix shape is valid, but the salt cannot match the allowed pattern (contains '!').
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(KEY_BYTES, SALT_WITH_INVALID_CHAR, PREFIX_WITH_DOLLARS));
    }

    @Test
    void saltNotMatchingPrefixIsRejected() {
        // Prefix shape is valid, but the salt does not begin with the required prefix.
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(KEY_BYTES, SALT_ALL_LETTERS, PREFIX_WITH_DOLLARS));
    }
}
