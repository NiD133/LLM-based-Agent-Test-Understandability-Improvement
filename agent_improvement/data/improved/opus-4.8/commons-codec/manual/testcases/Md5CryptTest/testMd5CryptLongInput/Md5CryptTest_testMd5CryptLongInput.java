package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * Verifies that the MD5-based {@code crypt()} algorithm correctly hashes a
 * plaintext password that is longer than the 16-byte MD5 block size.
 *
 * <p>The {@link Timeout} guards against the occasional hang that can occur while
 * running this hashing test.</p>
 */
@Timeout(3)
public class Md5CryptTest_testMd5CryptLongInput {

    /** A plaintext password (20 chars) longer than the 16-byte MD5 block. */
    private static final String LONG_PASSWORD = "12345678901234567890";

    /** MD5 crypt salt: the "$1$" prefix marks the MD5 variant, "1234" is the salt. */
    private static final String MD5_SALT = "$1$1234";

    /** The expected "$1$<salt>$<hash>" result for the password and salt above. */
    private static final String EXPECTED_HASH = "$1$1234$MoxekaNNUgfPRVqoeYjCD/";

    @Test
    void md5CryptHashesPasswordLongerThanBlockSize() {
        final String actualHash = Crypt.crypt(LONG_PASSWORD, MD5_SALT);

        assertEquals(EXPECTED_HASH, actualHash);
    }
}
