package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

// Try to avoid occasional hang when testing
@Timeout(3)
public class Md5CryptTest_testMd5CryptExplicitCallWithThreadLocalRandom {

    // MD5 crypt output format: $1$<salt (up to 8 chars)>$<hash>
    private static final String MD5_CRYPT_OUTPUT_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        // Verify that passing a ThreadLocalRandom generates a valid $1$ hash
        final String hashWithRandom = Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom);
        assertTrue(hashWithRandom.matches(MD5_CRYPT_OUTPUT_PATTERN));

        // Verify that passing a null salt also generates a valid $1$ hash (salt is auto-generated)
        final String hashWithNullSalt = Md5Crypt.md5Crypt("secret".getBytes(), (String) null);
        assertTrue(hashWithNullSalt.matches(MD5_CRYPT_OUTPUT_PATTERN));
    }
}
